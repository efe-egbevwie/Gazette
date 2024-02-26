package com.io.gazette.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.domain.models.NewsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news ORDER BY published_date desc")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT title, abstract, section, url, writer, published_date as publishedDate, photo_url AS photoUrl,  read_later_stories.read_later_collection_id as readLaterCollectionId  FROM news   LEFT JOIN read_later_stories on news.url = read_later_stories.story_url WHERE news.section = :section  GROUP BY url ORDER BY published_date desc")
    fun getNewsAndBookmarkCountBySection(section: String): Flow<List<NewsItem>>

    @Query("SELECT * FROM news where section=:section")
    fun getNewsBySection(section: String): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsItem: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(newsList: List<NewsEntity>)


}
