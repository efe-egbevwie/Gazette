package com.io.gazette.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.dao.ReadLaterDao
import com.io.gazette.data.local.model.NewsEntity
import com.io.gazette.data.local.model.ReadLaterCollectionEntity
import com.io.gazette.data.local.model.ReadLaterStoryEntity

@Database(
    entities = [NewsEntity::class, ReadLaterCollectionEntity::class, ReadLaterStoryEntity::class],
    version = 10,
    autoMigrations = [
        AutoMigration(from = 6, to = 7, spec = NewsDatabase.RenameReadLaterEntityMigration::class),
        AutoMigration(
            from = 7,
            to = 8,
            spec = NewsDatabase.RenameReadLaterListsToReadLaterCollectionMigration::class
        ),
        AutoMigration(from = 8, to = 9),
        AutoMigration(from = 9, to = 10)
    ],
    exportSchema = true
)
@TypeConverters(TimeStampTypeConverters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    abstract fun readLaterDao(): ReadLaterDao

    @RenameTable(fromTableName = "reading_list", toTableName = "read_later_lists")
    class RenameReadLaterEntityMigration : AutoMigrationSpec

    @RenameTable(fromTableName = "read_later_lists", toTableName = "read_later_collections")
    @RenameColumn(
        tableName = "read_later_lists",
        fromColumnName = "list_name",
        toColumnName = "collection_name"
    )
    @RenameColumn(
        tableName = "read_later_lists",
        fromColumnName = "list_id",
        toColumnName = "collection_id"
    )
    @RenameColumn(
        tableName = "read_later_stories",
        fromColumnName = "read_later_list_id",
        toColumnName = "read_later_collection_id"
    )
    class RenameReadLaterListsToReadLaterCollectionMigration : AutoMigrationSpec
}