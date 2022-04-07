package com.io.gazette.ui.home.businessNews

import com.io.gazette.domain.models.NewsItem

data class BusinessNewsScreenState(
    val isLoading:Boolean = false,
    val businessNews:List<NewsItem> = emptyList(),
    val errorMessage:String? = null
)