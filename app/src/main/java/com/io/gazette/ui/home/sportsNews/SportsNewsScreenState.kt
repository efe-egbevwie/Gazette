package com.io.gazette.ui.home.sportsNews

import com.io.gazette.domain.models.NewsItem

data class SportsNewsScreenState(
    val isLoading:Boolean = false,
    val sportsNews:List<NewsItem> = emptyList(),
    val errorMessage:String? = null
)