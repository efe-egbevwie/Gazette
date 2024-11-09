package com.io.gazette.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE `new_news` (" +
                    "`url` TEXT NOT NULL," +
                    "`title` TEXT NOT NULL, " +
                    "`abstract` TEXT NOT NULL," +
                    "`section` TEXT NOT NULL," +
                    "`photo_url` TEXT NOT NULL," +
                    "`writer` TEXT NOT NULL," +
                    "`published_date` TEXT," +
                    "PRIMARY KEY(`url`) )"
        )
        db.execSQL(
            "INSERT OR IGNORE INTO new_news(title, " +
                    "abstract," +
                    "section," +
                    "url," +
                    "photo_url," +
                    "writer," +
                    "published_date) " +
                    "SELECT title, abstract, section, url, photoUrl, writer, publishedDate FROM news"
        )
        db.execSQL("DROP table news")
        db.execSQL("ALTER TABLE new_news RENAME TO news")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `reading_list` (" +
                    "`list_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "`list_name` TEXT NOT NULL)"
        )
    }
}