package com.io.gazette.data.repositories


import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.data.remote.api.NytApi
import com.io.gazette.data.remote.api.models.toNewsEntity
import com.io.gazette.di.DeviceConnectivityUtil
import com.io.gazette.domain.models.GetDataResult
import com.io.gazette.domain.models.NewsCategory
import com.io.gazette.domain.models.NewsItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.SocketException
import javax.inject.Inject

class NytRepository @Inject constructor(
    private val nytApi: NytApi,
    private val newsDao: NewsDao,
    connectivityModule: DeviceConnectivityUtil
) {
    private val deviceHasInternetConnection = connectivityModule.hasInternetConnection()

    suspend fun refreshNews(category: NewsCategory): GetDataResult<Flow<List<NewsItem>>> {
        return try {
            if (!deviceHasInternetConnection) {
                return GetDataResult.Failure(exception = SocketException())
            }

            coroutineScope {
                launch { getNewsFromApi(section = NewsCategory.WORLD) }
                launch { getNewsFromApi(section = NewsCategory.BUSINESS) }
                launch { getNewsFromApi(section = NewsCategory.HEALTH) }
                launch { getNewsFromApi(section = NewsCategory.SPORTS) }
            }


            val section = when (category) {
                NewsCategory.WORLD -> "world"
                NewsCategory.BUSINESS -> "business"
                NewsCategory.HEALTH -> "health"
                NewsCategory.SPORTS -> "sports"
            }

            GetDataResult.Success(data = getNewsFromDb(section = section))
        } catch (e: Exception) {
            GetDataResult.Failure(exception = e)
        }

    }

    suspend fun getNewsByCategory(category: NewsCategory): GetDataResult<Flow<List<NewsItem>>> {
        return try {

            val section = when (category) {
                NewsCategory.WORLD -> "world"
                NewsCategory.BUSINESS -> "business"
                NewsCategory.HEALTH -> "health"
                NewsCategory.SPORTS -> "sports"
            }

            val newsFromDb = getNewsFromDb(section)

            newsFromDb.first().ifEmpty {
                Timber.i("news is empty")
                getNewsFromApi(category)
            }

            GetDataResult.Success(data = getNewsFromDb(section = section))
        } catch (e: Exception) {
            GetDataResult.Failure(exception = e)
        }

    }


    private fun getNewsFromDb(section: String): Flow<List<NewsItem>> {
        return newsDao.getNewsAndBookmarkCountBySection(section)
    }

    private suspend fun getNewsFromApi(section: NewsCategory) {
        try {
            val newsFromApi = when (section) {
                NewsCategory.WORLD -> nytApi.getTopWorldNews()
                NewsCategory.BUSINESS -> nytApi.getTopBusinessNews()
                NewsCategory.HEALTH -> nytApi.getTopHealthNews()
                NewsCategory.SPORTS -> nytApi.getTopSportsNews()
            }

            val newsFromApiResults = newsFromApi.results?.map { it.toNewsEntity(fallBackSection = section.name.lowercase()) }
            if (newsFromApiResults?.isNotEmpty() == true) saveNewsToDb(newsFromApiResults)
        } catch (e: Exception) {
            Timber.e("exception getting news from API: $e")
        }
    }

    private suspend fun saveNewsToDb(news: List<NewsEntity>) {
        newsDao.insertAllNews(news)
    }


}
