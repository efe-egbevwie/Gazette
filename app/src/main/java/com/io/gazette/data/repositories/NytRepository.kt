package com.io.gazette.data.repositories


import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.data.remote.api.NytApi
import com.io.gazette.data.remote.api.models.toNewsEntity
import com.io.gazette.di.DeviceConnectivityUtil
import com.io.gazette.domain.models.GetDataResult
import com.io.gazette.domain.models.NewsCategory
import com.io.gazette.domain.models.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEmpty
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

            getNewsFromApi(section = category)

            val section = when (category) {
                is NewsCategory.World -> "world"
                is NewsCategory.Business -> "business"
                is NewsCategory.Health -> "health"
                is NewsCategory.Sports -> "sports"
            }

            GetDataResult.Success(data = getNewsFromDb(section = section))
        } catch (e: Exception) {
            GetDataResult.Failure(exception = e)
        }

    }

    suspend fun getNewsByCategory(category: NewsCategory): GetDataResult<Flow<List<NewsItem>>> {
        return try {

            val section = when (category) {
                is NewsCategory.World -> "world"
                is NewsCategory.Business -> "business"
                is NewsCategory.Health -> "health"
                is NewsCategory.Sports -> "sports"
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
                NewsCategory.World -> nytApi.getTopWorldNews()
                NewsCategory.Business -> nytApi.getTopBusinessNews()
                NewsCategory.Health -> nytApi.getTopHealthNews()
                NewsCategory.Sports -> nytApi.getTopSportsNews()
            }

            val newsFromApiResults = newsFromApi.results?.map { it.toNewsEntity() }
            if (newsFromApiResults?.isNotEmpty() == true) saveNewsToDb(newsFromApiResults)
        } catch (e: Exception) {
            Timber.e("exception getting news from API: $e")
        }
    }

    private suspend fun saveNewsToDb(news: List<NewsEntity>) {
        newsDao.insertAllNews(news)
    }


}
