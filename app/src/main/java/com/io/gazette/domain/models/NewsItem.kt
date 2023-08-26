package com.io.gazette.domain.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class NewsItem(
    val title: String,
    val abstract: String,
    val section: String,
    val url: String,
    val photoUrl: String,
    val writer: String,
    private val publishedDate: LocalDateTime,
    val readLaterCollectionId: Int? = null
) {

    fun getFormattedDateTime() =
        this.publishedDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, uuuu, HH:mm a"))

    fun isValid(): Boolean = this.title.isNotBlank() and this.abstract.isNotBlank()

    fun isSavedToAnyCollection(): Boolean = this.readLaterCollectionId != null
}

