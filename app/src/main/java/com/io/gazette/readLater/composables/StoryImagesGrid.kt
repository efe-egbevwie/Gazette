package com.io.gazette.readLater.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.io.gazette.R


@Composable
fun ReadLaterStoriesImages(modifier: Modifier, imageUrls: List<String>) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .wrapContentSize()
            .horizontalScroll(scrollState)
    ) {

        imageUrls.forEach { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "first story image",
                placeholder = painterResource(id = R.drawable.ic_news_item),
                modifier = modifier
                    .size(150.dp)
                    .padding(2.dp),
                contentScale = ContentScale.Crop
            )
        }
    }


}

@Preview
@Composable
fun ReadLaterStoryImagesPreview() {
    val imageUrls = listOf(
        "https://static01.nyt.com/images/2023/08/14/multimedia/2023-07-06-trump-ga-indictment-index/2023-07-06-trump-ga-indictment-index-threeByTwoSmallAt2X-v7.jpg?format=pjpg&quality=75&auto=webp&disable=upscale",
        "https://static01.nyt.com/images/2023/08/24/multimedia/24spain-soccer-HFO-qwzh/24spain-soccer-HFO-qwzh-threeByTwoSmallAt2X.jpg?format=pjpg&quality=75&auto=webp&disable=upscale",
        "https://static01.nyt.com/images/2023/08/25/multimedia/25nat-hawaii-missing-wmbq/25nat-hawaii-missing-wmbq-threeByTwoSmallAt2X.jpg?format=pjpg&quality=75&auto=webp&disable=upscale"
    )

    ReadLaterStoriesImages(modifier = Modifier, imageUrls = imageUrls)
}