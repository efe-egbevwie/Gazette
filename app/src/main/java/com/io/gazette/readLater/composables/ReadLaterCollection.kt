package com.io.gazette.readLater.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.io.gazette.R
import com.io.gazette.common.ui.Pixel6APreview
import com.io.gazette.domain.models.ReadLaterCollection


@Composable
fun ReadLaterCollectionItem(
    readLaterCollection: ReadLaterCollection,
    onItemClicked: (collectionId: Int) -> Unit,
    onDeleteCollectionClicked: (collectionId: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onItemClicked.invoke(readLaterCollection.collectionId)
            },
        border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.colorPrimary)),
        elevation = 20.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {


            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = readLaterCollection.collectionTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    modifier = Modifier.weight(10f)
                )


                IconButton(
                    onClick = { onDeleteCollectionClicked.invoke(readLaterCollection.collectionId) },
                    modifier = Modifier.weight(1f)
                        .padding(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete read later collection"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val storyCountText = if (readLaterCollection.storyCount == 1){
                "${readLaterCollection.storyCount} story"
            }else{
                "${readLaterCollection.storyCount} stories"
            }

            Text(text = storyCountText, fontSize = 20.sp)
        }
    }
}


@Composable
fun ReadLaterCollectionsList(
    readLaterCollections: List<ReadLaterCollection>,
    onCollectionItemClicked: (collectionId: Int) -> Unit,
    onDeleteCollectionClicked: (collectionId: Int) -> Unit
) {
    LazyColumn {
        items(readLaterCollections) { collection ->
            ReadLaterCollectionItem(
                readLaterCollection = collection,
                onItemClicked = { onCollectionItemClicked.invoke(collection.collectionId) },
                onDeleteCollectionClicked = { onDeleteCollectionClicked.invoke(collection.collectionId) }
            )

        }
    }

}


@Pixel6APreview
@Composable
fun ReadLaterCollectionItemPreview() {
    ReadLaterCollectionItem(
        readLaterCollection = ReadLaterCollection(
            collectionTitle = "Medical Research",
            collectionId = 3,
            storyCount = 7
        ),
        onItemClicked = {},
        onDeleteCollectionClicked = {}
    )
}


@Pixel6APreview
@Composable
fun ReadLaterCollectionsListPreview() {
    val previewReadLaterCollections = listOf(
        ReadLaterCollection(
            collectionTitle = "Medical Research",
            collectionId = 3,
            storyCount = 7
        ),
        ReadLaterCollection(
            collectionTitle = "Music",
            collectionId = 4,
            storyCount = 1
        ),
        ReadLaterCollection(
            collectionTitle = "BasketBall",
            collectionId = 5,
            storyCount = 20
        ),
        ReadLaterCollection(
            collectionTitle = "Health",
            collectionId = 6,
            storyCount = 9
        ),
        ReadLaterCollection(
            collectionTitle = "Javascript",
            collectionId = 1,
            storyCount = 23
        )
    )

    ReadLaterCollectionsList(
        readLaterCollections = previewReadLaterCollections,
        onCollectionItemClicked = {},
        onDeleteCollectionClicked = {}
    )
}
