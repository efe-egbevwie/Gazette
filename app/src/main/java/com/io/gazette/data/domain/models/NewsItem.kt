package com.io.gazette.data.domain.models

import org.threeten.bp.LocalDateTime

data class NewsItem(
    val title: String,
    val abstract: String,
    val section: String,
    val url: String,
    val publishedDate: LocalDateTime,
    val photoUrl: String,
    val writer: String
)

