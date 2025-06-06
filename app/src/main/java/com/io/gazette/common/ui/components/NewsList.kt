package com.io.gazette.common.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.io.gazette.R
import com.io.gazette.common.ui.theme.GazetteTheme
import com.io.gazette.domain.models.NewsItem
import java.time.LocalDateTime

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
        items(items = newsItems, key = {news -> news.url}) { newsItem ->
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


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun NewsListItem(
    newsItem: NewsItem,
    modifier: Modifier = Modifier,
    onItemClick: (newsUrl: String) -> Unit,
    onSaveStoryButtonClicked: (storyUrl: String, storyImageUrl: String?) -> Unit
) {

    Card(
        onClick = {
            onItemClick(newsItem.url)
        },
        modifier = modifier,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
        ) {

            GlideImage(
                model = newsItem.photoUrl,
                contentDescription = "News Item Image",
                loading = placeholder(R.drawable.ic_news_item),
                failure = placeholder(R.drawable.ic_news_item),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
//            AsyncImage(
//                model = newsItem.photoUrl,
//                contentDescription = "News Item Image",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//                    .padding(bottom = 8.dp),
//                placeholder = painterResource(id = R.drawable.ic_news_item),
//                error = painterResource(id = R.drawable.ic_news_item)
//            )


            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = newsItem.title,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                )

                Text(
                    text = newsItem.abstract,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = newsItem.writer,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = newsItem.getFormattedDateTime(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                val imageResource = if (newsItem.isSavedToAnyCollection()) {
                    painterResource(id = R.drawable.ic_bookmark_icon_filled)
                } else {
                    painterResource(id = R.drawable.ic_bookmark_icon)
                }

                Image(
                    painter = imageResource,
                    contentDescription = "Bookmark",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onSaveStoryButtonClicked.invoke(
                                newsItem.url,
                                newsItem.photoUrl
                            )
                        }
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun PreviewNewsListItem() {
    val newsItem = NewsItem(
        title = "This is a sample news title",
        abstract = "This is a sample news title for 30th of march 2022" +
                "This is a sample news title for 30th of march 2022" +
                "This is a sample news title for 30th of march 2022" +
                "This is a sample news title for 30th of march 2022",
        section = "Business",
        photoUrl = "",
        url = "https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2338&q=80",
        writer = "Efe",
        publishedDate = LocalDateTime.now()

    )

    GazetteTheme {
        Surface {
            NewsListItem(
                modifier = Modifier.padding(10.dp),
                newsItem = newsItem,
                onItemClick = {},
                onSaveStoryButtonClicked = { _, _ -> })
        }
    }
}


@Composable
@PreviewLightDark
private fun NewsListPreview() {
    GazetteTheme {
        NewsList(
            modifier = Modifier.padding(10.dp),
            newsItems = sampleNewsList,
            onItemClick = {},
            onSaveStoryButtonClicked = { _, _ -> })
    }
}