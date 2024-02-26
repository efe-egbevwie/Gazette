package com.io.gazette.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "news")
data class NewsEntity(
    val title: String,
    val abstract: String,
    @ColumnInfo("section")
    val section: String,
    @PrimaryKey
    val url: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String,
    val writer: String,
    @ColumnInfo(name = "published_date")
    val publishedDate: LocalDateTime
)


