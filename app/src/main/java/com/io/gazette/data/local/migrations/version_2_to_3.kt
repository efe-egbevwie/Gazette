package com.io.gazette.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
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


        database.execSQL(
            "INSERT OR IGNORE INTO new_news(title, " +
                    "abstract," +
                    "section," +
                    "url," +
                    "photo_url," +
                    "writer," +
                    "published_date) " +
                    "SELECT title, abstract, section, url, photoUrl, writer, publishedDate FROM news"
        )

        database.execSQL("DROP table news")

        database.execSQL("ALTER TABLE new_news RENAME TO news")


    }

}