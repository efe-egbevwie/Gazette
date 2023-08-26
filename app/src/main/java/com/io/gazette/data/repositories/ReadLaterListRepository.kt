package com.io.gazette.data.repositories


import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.model.ReadLaterCollectionEntity
import com.io.gazette.data.local.model.ReadLaterStoryEntity
import com.io.gazette.data.local.model.toDomainRedLaterCollection
import com.io.gazette.domain.models.ReadLaterCollection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadLaterListRepository @Inject constructor(private val newsDao: NewsDao) {

    suspend fun addNewReadLaterCollection(listTitle: String) {
        val newReadLaterCollectionEntity = ReadLaterCollectionEntity(collectionName = listTitle)
        newsDao.createReadingList(newReadLaterCollectionEntity)
    }


    fun getReadLaterCollections(): Flow<List<ReadLaterCollection>> {
        return newsDao.getReadLaterCollections().map { readLaterListEntity ->
            readLaterListEntity.map { entity ->
                entity.toDomainRedLaterCollection()
            }
        }
    }

    fun getReadLaterCollectionsAndInfo(): Flow<List<ReadLaterCollection>> {
        return newsDao.getReadLaterCollectionsAndInfo()
    }

    suspend fun saveStoryToReadLaterCollections(storyUrl: String, storyImageUrl:String? = null, readLaterCollectionIds: List<Int>) {
        readLaterCollectionIds.forEach { id ->
            val readLaterStory = ReadLaterStoryEntity(
                storyUrl = storyUrl,
                storyImageUrl  = storyImageUrl,
                readLaterCollectionId = id
            )
            newsDao.saveNewsItemToReadLaterCollection(readLaterStory)
        }
    }

}