package com.io.gazette.news.sportsNews

import com.io.gazette.common.OneTimeEvent
import com.io.gazette.domain.models.NewsItem

data class SportsNewsScreenState(
    val isLoading:Boolean = false,
    val sportsNews:List<NewsItem> = emptyList(),
    val error:OneTimeEvent<Exception>? = null,
    val errorMessage:String? = null
)