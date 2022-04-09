package com.io.gazette.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.io.gazette.domain.models.NewsItem

@Composable
fun NewsList(
    newsItems: List<NewsItem>,
    modifier: Modifier = Modifier,
    onItemClick: (NewsItem) -> Unit,
) {

    val listState = rememberLazyListState()
    LazyColumn(modifier.fillMaxSize(), state = listState) {
        items(newsItems) { newsItem ->
            NewsListItem(newsItem = newsItem, onItemClick = onItemClick)
        }
    }
}