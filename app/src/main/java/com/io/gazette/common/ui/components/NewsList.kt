package com.io.gazette.common.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.io.gazette.domain.models.NewsItem
import timber.log.Timber

@Composable
fun NewsList(
    newsItems: List<NewsItem>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    onSaveStoryButtonClicked: (storyUrl: String, storyImageUrl: String?) -> Unit
) {

    Timber.i("read later items: $newsItems")

    val listState = rememberLazyListState()

    LazyColumn(
        modifier
            .fillMaxSize(),
        state = listState,
    ) {
        items(newsItems) { newsItem ->
            if (newsItem.isValid()) NewsListItem(
                newsItem = newsItem,
                onItemClick = onItemClick,
                onSaveStoryButtonClicked = onSaveStoryButtonClicked
            )
        }
    }

}


@Composable
@Preview(
    device = "id:pixel_6a", showSystemUi = true, showBackground = true,
    wallpaper = Wallpapers.NONE
)
fun NewsListPreview() {
    NewsList(newsItems = sampleNewsList, onItemClick = {}, onSaveStoryButtonClicked = { _, _ -> })
}