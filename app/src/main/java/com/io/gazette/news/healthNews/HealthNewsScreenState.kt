package com.io.gazette.news.healthNews

import com.io.gazette.common.OneTimeEvent
import com.io.gazette.domain.models.NewsItem

data class HealthNewsScreenState(
    val isLoading: Boolean = false,
    val healthNews: List<NewsItem> = emptyList(),
    val error: OneTimeEvent<Exception>? = null,
    val errorMessage: String? = null
)