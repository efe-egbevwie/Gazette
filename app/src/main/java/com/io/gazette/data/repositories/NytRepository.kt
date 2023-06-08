package com.io.gazette.data.repositories


import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.data.remote.api.NytApi
import com.io.gazette.data.remote.api.models.toNewsEntity
import com.io.gazette.di.DeviceConnectivityUtil
import com.io.gazette.domain.models.GetDataResult
import com.io.gazette.domain.models.NewsItem
import com.io.gazette.domain.models.NewsSection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class NytRepository @Inject constructor(
    private val nytApi: NytApi,
    private val newsDao: NewsDao,
    connectivityModule: DeviceConnectivityUtil
) {
    private val deviceHasInternetConnection = connectivityModule.hasInternetConnection()
    suspend fun getWorldNews(): GetDataResult<Flow<List<NewsItem>>> {

        return try {
            if (deviceHasInternetConnection) {
                getNewsFromApi(section = NewsSection.WORLD)
            }

            GetDataResult.Success(data = getNewsFromDb(section = "world"))
        } catch (e: Exception) {
            GetDataResult.Failure(exception = e)
        }

    }

    suspend fun getBusinessNews(): GetDataResult<Flow<List<NewsItem>>> {
        return try {
            if (deviceHasInternetConnection) {
                getNewsFromApi(section = NewsSection.BUSINESS)
            }

            GetDataResult.Success(data = getNewsFromDb(section = "business"))
        } catch (e: Exception) {
            GetDataResult.Failure(exception = e)
        }

    }

    suspend fun getSportsNews(): GetDataResult<Flow<List<NewsItem>>> {
        return try {
            if (deviceHasInternetConnection) {
                getNewsFromApi(section = NewsSection.SPORTS)
            }

            GetDataResult.Success(data = getNewsFromDb(section = "sports"))
        } catch (e: Exception) {
            GetDataResult.Failure(exception = e)
        }
    }

    suspend fun getHealthNews(): GetDataResult<Flow<List<NewsItem>>> {
        return try {
            if (deviceHasInternetConnection) {
                getNewsFromApi(section = NewsSection.HEALTH)
            }

            GetDataResult.Success(data = getNewsFromDb(section = "health"))
        } catch (e: Exception) {
            GetDataResult.Failure(exception = e)
        }
    }


    private fun getNewsFromDb(section: String): Flow<List<NewsItem>> {
        return newsDao.getNewsBySection(section).map { newsListFromDb ->
            newsListFromDb.map { newsItem ->
                newsItem.toDomainNewsItem()
            }
        }
    }

    private suspend fun getNewsFromApi(section: NewsSection) {
        try {
            val newsFromApi = when (section) {
                NewsSection.WORLD -> nytApi.getTopWorldNews()
                NewsSection.BUSINESS -> nytApi.getTopBusinessNews()
                NewsSection.HEALTH -> nytApi.getTopHealthNews()
                NewsSection.SPORTS -> nytApi.getTopSportsNews()
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
