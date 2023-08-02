package com.io.gazette.common.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.io.gazette.R
import com.io.gazette.domain.models.NewsItem

@Preview(
    device = "id:pixel_4a",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewNewsListItem() {
    val newsItem = NewsItem(
        title = "This is a sample news title ",
        abstract = "This is a sample news title for 30th of march 2022" +
                "This is a sample news title for 30th of march 2022" +
                "This is a sample news title for 30th of march 2022" +
                "This is a sample news title for 30th of march 2022",
        section = "Business",
        photoUrl = "",
        url = "https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2338&q=80",
        writer = "Efe",
        publishedDate = "14/1/2023"
    )

    NewsListItem(newsItem = newsItem, onItemClick = {}, onSaveStoryButtonClicked = {})
}


@Composable
fun NewsListItem(
    newsItem: NewsItem,
    modifier: Modifier = Modifier,
    onItemClick: (newsUrl: String) -> Unit,
    onSaveStoryButtonClicked: (storyUrl:String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .clickable { onItemClick(newsItem.url) },
        border = BorderStroke(1.dp, color = MaterialTheme.colors.onSurface),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {

        ConstraintLayout(
            modifier = modifier.fillMaxSize()
        ) {
            val (photo,
                title,
                author,
                abstract,
                publishedDate,
                bookmarkIcon
            ) = createRefs()

            AsyncImage(
                model = newsItem.photoUrl,
                contentDescription = "News Item Image",
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.20F)
                    .padding(8.dp)
                    .constrainAs(photo) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(title.top)
                    },
                placeholder = painterResource(id = R.drawable.ic_news_item)

            )


            Text(
                text = newsItem.title,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = modifier
                    .padding(top = 16.dp, end = 32.dp, start = 16.dp)
                    .constrainAs(title) {
                        start.linkTo(parent.start)
                        top.linkTo(photo.bottom)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )


            Text(
                text = newsItem.abstract,
                fontSize = 18.sp,
                modifier = modifier
                    .padding(top = 8.dp, start = 16.dp, end = 32.dp)
                    .constrainAs(abstract) {
                        start.linkTo(title.start)
                        top.linkTo(title.bottom)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )



            Text(
                text = newsItem.writer,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .constrainAs(author) {
                        start.linkTo(abstract.start)
                        top.linkTo(abstract.bottom)
                        end.linkTo(parent.end)
                    }
            )

            Text(text = newsItem.publishedDate.orEmpty(),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(0.5f)
                    .constrainAs(publishedDate) {
                        top.linkTo(author.bottom)
                        linkTo(start = author.start, end = parent.end, bias = 0F)
                    }

            )

            Image(
                painter = painterResource(id = R.drawable.ic_bookmark_icon_filled),
                contentDescription = "Bookmark",
                modifier = modifier
                    .size(40.dp)
                    .padding(10.dp)
                    .constrainAs(bookmarkIcon) {
                        baseline.linkTo(publishedDate.baseline)
                        end.linkTo(parent.end)
                        bottom.linkTo(publishedDate.bottom)
                    }
                    .clickable { onSaveStoryButtonClicked.invoke(newsItem.url) }
            )

        }

    }
}