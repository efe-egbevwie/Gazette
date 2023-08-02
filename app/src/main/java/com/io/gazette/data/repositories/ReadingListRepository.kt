package com.io.gazette.data.repositories


import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.model.ReadLaterStoryEntity
import com.io.gazette.data.local.model.ReadLaterListEntity
import com.io.gazette.data.local.model.toDomainReadLaterList
import com.io.gazette.domain.models.ReadLaterList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadingListRepository @Inject constructor(private val newsDao: NewsDao) {

    suspend fun addNewReadingList(listTitle: String) {
        val newReadlaterListEntity = ReadLaterListEntity(listName = listTitle)
        newsDao.createReadingList(newReadlaterListEntity)
    }


    fun getReadLaterLists(): Flow<List<ReadLaterList>> {
        return newsDao.getReadLaterLists().map { readLaterListEntity ->
            readLaterListEntity.map { entity ->
                entity.toDomainReadLaterList()
            }
        }
    }

    suspend fun saveStoryToReadingLists(storyUrl: String, readingListsIds: List<Int>) {
        readingListsIds.forEach { id ->
            val readLaterStory = ReadLaterStoryEntity(
                storyUrl,
                id
            )
            newsDao.saveNewsItemToReadLaterList(readLaterStory)
        }
    }
}