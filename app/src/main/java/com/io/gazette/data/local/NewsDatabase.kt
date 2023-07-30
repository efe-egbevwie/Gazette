package com.io.gazette.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.data.local.model.ReadingList

@Database(
    entities = [NewsEntity::class, ReadingList::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}