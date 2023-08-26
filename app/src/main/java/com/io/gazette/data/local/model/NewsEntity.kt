package com.io.gazette.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.io.gazette.domain.models.NewsItem
import java.time.LocalDateTime

@Entity(tableName = "news")
data class NewsEntity(
    val title: String,
    val abstract: String,
    val section: String,
    @PrimaryKey
    val url: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String,
    val writer: String,
    @ColumnInfo(name = "published_date")
    val publishedDate: LocalDateTime
) {
    fun toDomainNewsItem() = NewsItem(
        title, abstract, section, url, photoUrl, writer, publishedDate
    )
}


