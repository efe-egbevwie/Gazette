package com.io.gazette.data.repositories


import com.io.gazette.data.local.dao.ReadLaterDao
import com.io.gazette.data.local.model.ReadLaterCollectionEntity
import com.io.gazette.data.local.model.ReadLaterStoryEntity
import com.io.gazette.data.local.model.toDomainRedLaterCollection
import com.io.gazette.domain.models.NewsItem
import com.io.gazette.domain.models.ReadLaterCollection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadLaterListRepository @Inject constructor(private val readLaterDao: ReadLaterDao) {

    suspend fun addNewReadLaterCollection(listTitle: String) {
        val newReadLaterCollectionEntity = ReadLaterCollectionEntity(collectionName = listTitle)
        readLaterDao.createReadingList(newReadLaterCollectionEntity)
    }


    suspend fun deleteReadLaterCollection(collectionId: Int) {
        readLaterDao.deleteReadLaterCollection(collectionId)
    }

    suspend fun deleteStoryFromCollection(storyUrl: String) {
        readLaterDao.deleteStoryFromCollection(storyUrl)
    }

    fun getReadLaterCollections(): Flow<List<ReadLaterCollection>> {
        return readLaterDao.getReadLaterCollections().map { readLaterListEntity ->
            readLaterListEntity.map { entity ->
                entity.toDomainRedLaterCollection()
            }
        }
    }

    fun getReadLaterCollectionsAndInfo(): Flow<List<ReadLaterCollection>> {
        return readLaterDao.getReadLaterCollectionsAndInfo()
    }

    suspend fun saveStoryToReadLaterCollections(
        storyUrl: String,
        storyImageUrl: String? = null,
        readLaterCollectionIds: List<Int>
    ) {
        readLaterCollectionIds.forEach { id ->
            val readLaterStory = ReadLaterStoryEntity(
                storyUrl = storyUrl,
                storyImageUrl = storyImageUrl,
                readLaterCollectionId = id
            )
            readLaterDao.saveNewsItemToReadLaterCollection(readLaterStory)
        }
    }

    fun getStoriesInCollection(collectionId: Int): Flow<List<NewsItem>> {
        return readLaterDao.getAllItemsForReadLaterCollection(collectionId)
    }

}