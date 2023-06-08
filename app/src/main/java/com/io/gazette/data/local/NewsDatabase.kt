package com.io.gazette.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.model.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class NewsDatabase:RoomDatabase() {
    abstract fun newsDao(): NewsDao
}