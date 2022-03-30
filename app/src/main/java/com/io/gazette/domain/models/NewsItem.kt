package com.io.gazette.domain.models

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime


data class NewsItem(
    val title: String,
    val abstract: String,
    val section: String,
    val url: String,
    val photoUrl: String,
    val writer: String
)

