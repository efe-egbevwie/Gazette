package com.io.gazette.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.io.gazette.R
import com.io.gazette.domain.models.NewsItem

@Preview
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
        url = "",
        writer = "Efe"
    )

    NewsListItem(newsItem = newsItem, onItemClick = {} )
}


@Composable
fun NewsListItem(
    newsItem: NewsItem,
    modifier: Modifier = Modifier,
    onItemClick: (NewsItem) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .clickable { onItemClick(newsItem) },
        border = BorderStroke(1.dp, color = MaterialTheme.colors.onSurface),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {

        ConstraintLayout(
            modifier = modifier.fillMaxSize()
        ) {
            val (photo, title, author, abstract) = createRefs()

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
                    }

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
                modifier = modifier
                    .padding(16.dp)
                    .constrainAs(author) {
                        start.linkTo(abstract.start)
                        top.linkTo(abstract.bottom)
                    }
            )

        }

    }
}