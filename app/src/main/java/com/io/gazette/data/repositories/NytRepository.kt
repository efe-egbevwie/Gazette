package com.io.gazette.data.repositories


import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.data.remote.api.NytApi
import com.io.gazette.data.remote.api.models.toNewsEntity
import com.io.gazette.di.DeviceConnectivityUtil
import com.io.gazette.domain.models.NewsCategory
import com.io.gazette.domain.models.NewsItem
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class NytRepository @Inject constructor(
    private val nytApi: NytApi,
    private val newsDao: NewsDao,
    connectivityModule: DeviceConnectivityUtil
) {
    private val deviceHasInternetConnection = connectivityModule.hasInternetConnection()

    suspend fun refreshNews(): Flow<List<NewsItem>> = coroutineScope {
        val allNews = listOf(
            async { getNewsFromApi(section = NewsCategory.WORLD) },
            async { getNewsFromApi(section = NewsCategory.HEALTH) },
            async { getNewsFromApi(section = NewsCategory.BUSINESS) },
            async { getNewsFromApi(section = NewsCategory.SPORTS) }
        )
        awaitAll(*allNews.toTypedArray())
        getNewsFromDb()
    }

    suspend fun getNews(): Flow<List<NewsItem>> {
        val newsFromDb = getNewsFromDb()
        newsFromDb.first().ifEmpty {
            refreshNews()
        }
        return getNewsFromDb()
    }

    suspend fun getNewsByCategory(category: NewsCategory): Flow<List<NewsItem>> {
        val newsFromDb = getNewsFromDb(category.name.lowercase())
        newsFromDb.first().ifEmpty {
            Timber.i("news is empty")
            getNewsFromApi(category)
        }
        return getNewsFromDb(section = category.name.lowercase())
    }


    private fun getNewsFromDb(section: String? = null): Flow<List<NewsItem>> {
        return if (section.isNullOrBlank()) {
            return newsDao.getAllNewsAndBookmarkCount()
        } else {
            newsDao.getNewsAndBookmarkCountBySection(section)
        }
    }

    private suspend fun getNewsFromApi(section: NewsCategory) {
        try {
            val newsFromApi = when (section) {
                NewsCategory.WORLD -> nytApi.getTopWorldNews()
                NewsCategory.BUSINESS -> nytApi.getTopBusinessNews()
                NewsCategory.HEALTH -> nytApi.getTopHealthNews()
                NewsCategory.SPORTS -> nytApi.getTopSportsNews()
            }

            val newsFromApiResults =
                newsFromApi.results?.map { it.toNewsEntity(fallBackSection = section.name.lowercase()) }
            if (newsFromApiResults?.isNotEmpty() == true) saveNewsToDb(newsFromApiResults)
        } catch (e: Exception) {
            Timber.e("exception getting news from API: $e")
        }
    }

    private suspend fun saveNewsToDb(news: List<NewsEntity>) {
        newsDao.insertAllNews(news)
    }
}
