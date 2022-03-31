package com.io.gazette.ui.home

import com.io.gazette.domain.models.NewsItem

data class WorldNewsScreenState(
    val isLoadingWorldNews:Boolean = false,
    val worldNews:List<NewsItem> = emptyList(),
    val worldNewsErrorMessage:String? = null,
)