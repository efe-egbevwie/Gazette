package com.io.gazette.news.worldNews

import com.io.gazette.common.OneTimeEvent
import com.io.gazette.domain.models.NewsItem

data class WorldNewsScreenState(
    val isLoadingWorldNews:Boolean = false,
    val worldNews: List<NewsItem>? = emptyList(),
    val error:OneTimeEvent<Exception>? = null,
    val worldNewsErrorMessage:String? = null,
)