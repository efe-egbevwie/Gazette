package com.io.gazette.common.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.io.gazette.common.ui.theme.GazetteTheme
import com.io.gazette.domain.models.NewsItem

@Composable
fun NewsList(
    newsItems: List<NewsItem>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    listState: LazyListState = rememberLazyListState(),
    onSaveStoryButtonClicked: (storyUrl: String, storyImageUrl: String?) -> Unit
) {
    LazyColumn(
        modifier,
        state = listState,
    ) {
        items(newsItems) { newsItem ->
            if (newsItem.isValid()) {
                NewsListItem(
                    modifier = Modifier.padding(bottom = 10.dp),
                    newsItem = newsItem,
                    onItemClick = onItemClick,
                    onSaveStoryButtonClicked = onSaveStoryButtonClicked
                )
            }
        }
    }
}


@Composable
@PreviewLightDark
fun NewsListPreview() {
    GazetteTheme {
        NewsList(
            modifier = Modifier.padding(10.dp),
            newsItems = sampleNewsList,
            onItemClick = {},
            onSaveStoryButtonClicked = { _, _ -> })
    }
}