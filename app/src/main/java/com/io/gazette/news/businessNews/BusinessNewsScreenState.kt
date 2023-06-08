package com.io.gazette.news.businessNews

import com.io.gazette.common.OneTimeEvent
import com.io.gazette.domain.models.NewsItem

data class BusinessNewsScreenState(
    val isLoading:Boolean = false,
    val businessNews:List<NewsItem> = emptyList(),
    val error:OneTimeEvent<Exception>? = null,
    val errorMessage:String? = null
)