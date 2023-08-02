package com.io.gazette.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.data.local.model.ReadLaterStoryEntity
import com.io.gazette.data.local.model.ReadLaterListEntity

@Database(
    entities = [NewsEntity::class, ReadLaterListEntity::class, ReadLaterStoryEntity::class],
    version = 7,
    autoMigrations = [
        AutoMigration(from = 6, to = 7, spec = NewsDatabase.RenameReadLaterEntityMigration::class),

    ],
    exportSchema = true
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    @RenameTable(fromTableName = "reading_list", toTableName = "read_later_lists")
    class RenameReadLaterEntityMigration: AutoMigrationSpec
}