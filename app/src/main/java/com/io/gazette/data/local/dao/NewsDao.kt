package com.io.gazette.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.data.local.model.ReadLaterListEntity
import com.io.gazette.data.local.model.ReadLaterStoryEntity
import com.io.gazette.domain.models.ReadLaterCollection
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

    @Query("SELECT list_name AS listName, list_id AS listId, COUNT(read_later_list_id) AS storyCount from read_later_lists LEFT JOIN read_later_stories on read_later_list_id = list_id GROUP BY read_later_list_id")
    fun getReadLaterListsAndInfo():Flow<List<ReadLaterCollection>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNewsItemToReadLaterList(readLaterStoryEntity: ReadLaterStoryEntity)

    @Query("SELECT * from news JOIN read_later_stories ON news.url = read_later_stories.story_url where read_later_stories.read_later_list_id = :readLaterListId")
    fun getAllItemsForReadingList(readLaterListId: Int): Flow<List<NewsEntity>>

    @Query("SELECT COUNT(*) from read_later_stories where read_later_list_id = :readLaterListId")
    suspend fun getAmountOfStoriesInReadLaterList(readLaterListId: Int): Int
}
