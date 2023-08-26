package com.io.gazette.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.data.local.model.ReadLaterCollectionEntity
import com.io.gazette.data.local.model.ReadLaterStoryEntity
import com.io.gazette.domain.models.NewsItem
import com.io.gazette.domain.models.ReadLaterCollection
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllNews(newsList: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createReadingList(readLaterCollectionEntity: ReadLaterCollectionEntity)

    @Query("SELECT * from read_later_collections")
    fun getReadLaterCollections(): Flow<List<ReadLaterCollectionEntity>>

    @Query("SELECT collection_name AS collectionTitle,GROUP_CONCAT(image_url, ',') AS imageUrls ,collection_id AS collectionId, COUNT(read_later_collection_id) AS storyCount from read_later_collections LEFT JOIN read_later_stories on read_later_collection_id = collection_id GROUP BY read_later_collection_id")
    fun getReadLaterCollectionsAndInfo():Flow<List<ReadLaterCollection>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNewsItemToReadLaterCollection(readLaterStoryEntity: ReadLaterStoryEntity)

    @Query("SELECT * from news JOIN read_later_stories ON news.url = read_later_stories.story_url where read_later_stories.read_later_collection_id = :readLaterCollectionId")
    fun getAllItemsForReadLaterCollection(readLaterCollectionId: Int): Flow<List<NewsEntity>>

    @Query("SELECT COUNT(*) from read_later_stories where read_later_collection_id = :readLaterCollectionId")
    suspend fun getAmountOfStoriesInReadLaterCollection(readLaterCollectionId: Int): Int
}
