package com.io.gazette.domain.models

import com.io.gazette.data.local.model.NewsEntity


data class NewsItem(
    val title: String,
    val abstract: String,
    val section: String,
    val url: String,
    val photoUrl: String,
    val writer: String,
    val publishedDate: String? = null,
    val readLaterCollectionId: Int? = null
) {
    fun isValid(): Boolean = this.title.isNotBlank() and this.abstract.isNotBlank()

    fun  isSavedToAnyCollection():Boolean = this.readLaterCollectionId != null
}

fun NewsItem.toNewsEntity() = NewsEntity(
    title = this.title,
    abstract = this.abstract,
    section = this.section,
    url = this.url,
    photoUrl = this.photoUrl,
    writer = this.writer,
    publishedDate = this.publishedDate
)

