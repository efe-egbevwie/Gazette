package com.io.gazette.readLater.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.io.gazette.R


@Composable
fun ReadLaterStoriesImages(modifier: Modifier, imageUrls: List<String>) {
    LazyRow(modifier = modifier) {
        items(items = imageUrls){imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "first story image",
                placeholder = painterResource(id = R.drawable.ic_news_item),
                error = painterResource(id = R.drawable.ic_news_item),
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = androidx.compose.ui.graphics.Color.LightGray
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .size(150.dp)
                    .padding(2.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ReadLaterStoryImagesPreview() {
    val imageUrls = listOf(
        "https://static01.nyt.com/images/2023/08/14/multimedia/2023-07-06-trump-ga-indictment-index/2023-07-06-trump-ga-indictment-index-threeByTwoSmallAt2X-v7.jpg?format=pjpg&quality=75&auto=webp&disable=upscale",
        "https://static01.nyt.com/images/2023/08/24/multimedia/24spain-soccer-HFO-qwzh/24spain-soccer-HFO-qwzh-threeByTwoSmallAt2X.jpg?format=pjpg&quality=75&auto=webp&disable=upscale",
        "https://static01.nyt.com/images/2023/08/25/multimedia/25nat-hawaii-missing-wmbq/25nat-hawaii-missing-wmbq-threeByTwoSmallAt2X.jpg?format=pjpg&quality=75&auto=webp&disable=upscale",
        "https://static01.nyt.com/images/2023/08/25/multimedia/25nat-hawaii-missing-wmbq/25nat-hawaii-missing-wmbq-threeByTwoSmallAt2X.jpg?format=pjpg&quality=75&auto=webp&disable=upscale",
        "https://static01.nyt.com/images/2023/08/25/multimedia/25nat-hawaii-missing-wmbq/25nat-hawaii-missing-wmbq-threeByTwoSmallAt2X.jpg?format=pjpg&quality=75&auto=webp&disable=upscale",
        "https://static01.nyt.com/images/2023/08/25/multimedia/25nat-hawaii-missing-wmbq/25nat-hawaii-missing-wmbq-threeByTwoSmallAt2X.jpg?format=pjpg&quality=75&auto=webp&disable=upscale"
    )

    ReadLaterStoriesImages(modifier = Modifier, imageUrls = imageUrls)
}