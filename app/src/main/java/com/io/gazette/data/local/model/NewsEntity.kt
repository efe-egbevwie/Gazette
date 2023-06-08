package com.io.gazette.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.io.gazette.domain.models.NewsItem

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val abstract: String,
    val section: String,
    val url: String,
    val photoUrl: String,
    val writer: String,
    val publishedDate: String? = null
){
    fun toDomainNewsItem() = NewsItem(
        title, abstract, section, url, photoUrl, writer, publishedDate
    )
}


