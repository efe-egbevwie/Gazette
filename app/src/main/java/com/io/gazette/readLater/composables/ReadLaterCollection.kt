package com.io.gazette.readLater.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.io.gazette.common.ui.Pixel6APreview
import com.io.gazette.domain.models.ReadLaterCollection


@Composable
fun ReadLaterCollectionItem(
    readLaterCollection: ReadLaterCollection,
    onItemClicked: (collectionId: Int) -> Unit,
    onDeleteCollectionClicked: (collectionId: Int) -> Unit,
    firstThreeStoryImageUrls: List<String>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
) {

    Card(
        modifier = modifier,
        onClick = {
            onItemClicked.invoke(readLaterCollection.collectionId)
        },
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary
        ) ,
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        var showDeleteDialog by rememberSaveable {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = readLaterCollection.collectionTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    modifier = Modifier.weight(10f)
                )


                IconButton(
                    onClick = {
                        showDeleteDialog = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete read later collection"
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            val storyCountText = if (readLaterCollection.storyCount == 1) {
                "${readLaterCollection.storyCount} story"
            } else {
                "${readLaterCollection.storyCount} stories"
            }

            Text(
                text = storyCountText,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            ReadLaterStoriesImages(
                modifier = Modifier,
                imageUrls = firstThreeStoryImageUrls
            )

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDeleteDialog = false
                    },
                    title = {
                        Column {
                            Text(
                                text = "Are you sure you want to delete \"${readLaterCollection.collectionTitle}\" "
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(10.dp)
                            )
                            Text(
                                text = "All Stories saved to this collection will be deleted",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Normal
                            )
                        }

                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDeleteCollectionClicked(readLaterCollection.collectionId)
                                showDeleteDialog = false
                            }
                        ) {
                            Text(
                                text = "Delete"
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDeleteDialog = false
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun ReadLaterCollectionsList(
    modifier: Modifier = Modifier,
    readLaterCollections: List<ReadLaterCollection>,
    onCollectionItemClicked: (collectionId: Int, collectionTitle: String) -> Unit,
    onDeleteCollectionClicked: (collectionId: Int) -> Unit
) {
    LazyColumn (modifier = modifier){
        items(readLaterCollections) { collection: ReadLaterCollection ->
            ReadLaterCollectionItem(
                readLaterCollection = collection,
                onItemClicked = {
                    onCollectionItemClicked.invoke(
                        collection.collectionId,
                        collection.collectionTitle
                    )
                },
                onDeleteCollectionClicked = { onDeleteCollectionClicked.invoke(collection.collectionId) },
                firstThreeStoryImageUrls = collection.getThreeImageUrls() ?: emptyList()
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
        onDeleteCollectionClicked = {},
        firstThreeStoryImageUrls = listOf("", "", "")
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
        onCollectionItemClicked = { _: Int, _: String -> },
        onDeleteCollectionClicked = { }
    )
}
