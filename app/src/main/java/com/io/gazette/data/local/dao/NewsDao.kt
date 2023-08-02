package com.io.gazette.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.data.local.model.ReadLaterStoryEntity
import com.io.gazette.data.local.model.ReadLaterListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news where section=:section")
    fun getNewsBySection(section: String): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsItem: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllNews(newsList: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createReadingList(readLaterListEntity: ReadLaterListEntity)

    @Query("SELECT * from read_later_lists")
    fun getReadLaterLists(): Flow<List<ReadLaterListEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNewsItemToReadLaterList(readLaterStoryEntity: ReadLaterStoryEntity)

    @Query("SELECT * from news JOIN read_later_stories ON news.url = read_later_stories.story_url where read_later_stories.read_later_list_id = :readLaterListId")
    fun getAllItemsForReadingList(readLaterListId: Int):Flow<List<NewsEntity>>

}