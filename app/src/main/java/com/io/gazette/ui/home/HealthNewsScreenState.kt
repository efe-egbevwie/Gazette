package com.io.gazette.ui.home

import com.io.gazette.domain.models.NewsItem

data class HealthNewsScreenState(
    val isLoading: Boolean = false,
    val healthNews: List<NewsItem> = emptyList(),
    val errorMessage: String? = null
)