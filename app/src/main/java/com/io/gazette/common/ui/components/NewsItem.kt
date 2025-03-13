package com.io.gazette.common.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.io.gazette.R
import com.io.gazette.common.ui.theme.GazetteTheme
import com.io.gazette.domain.models.NewsItem
import java.time.LocalDateTime

@Composable
fun NewsListItem(
    newsItem: NewsItem,
    modifier: Modifier = Modifier,
    onItemClick: (newsUrl: String) -> Unit,
    onSaveStoryButtonClicked: (storyUrl: String, storyImageUrl: String?) -> Unit
) {

    Card(
        modifier = modifier
            .wrapContentHeight()
            .clickable { onItemClick(newsItem.url) },
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onSurface),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
        ) {

            AsyncImage(
                model = newsItem.photoUrl,
                contentDescription = "News Item Image",
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.20F),
                placeholder = painterResource(id = R.drawable.ic_news_item),
                error = painterResource(id = R.drawable.ic_news_item)
            )


            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = newsItem.title,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = modifier
                )

                Text(
                    text = newsItem.abstract,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier
                )

                Text(
                    text = newsItem.writer,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Start,
                    modifier = modifier
                        .fillMaxWidth()
                )

                Text(
                    text = newsItem.getFormattedDateTime(),
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Start,
                    modifier = modifier
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
                    modifier = modifier
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
fun PreviewNewsListItem() {
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
