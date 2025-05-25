package com.io.gazette.readLater.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.io.gazette.R
import com.io.gazette.common.ui.theme.GazetteTheme
import com.io.gazette.domain.models.ReadLaterCollection


@Composable
fun AddToReadLaterList(
    readLaterList: List<ReadLaterCollection>,
    modifier: Modifier = Modifier,
    onItemChecked: (readingListId: Int) -> Unit,
    onItemUnchecked: (readingListId: Int) -> Unit
) {
    LazyColumn(modifier) {
        items(readLaterList) { listItem ->
            AddToReadLaterItem(
                onItemChecked = onItemChecked,
                onItemUnchecked = onItemUnchecked,
                readLaterList = listItem
            )
        }
    }
}


@Composable
fun AddToReadLaterItem(
    onItemChecked: (readingListId: Int) -> Unit,
    onItemUnchecked: (readingListId: Int) -> Unit,
    readLaterList: ReadLaterCollection
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        var isItemChecked by remember { mutableStateOf(false) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable {
                    isItemChecked = !isItemChecked
                    if (isItemChecked) {
                        onItemChecked.invoke(readLaterList.collectionId)
                    } else {
                        onItemUnchecked.invoke(readLaterList.collectionId)
                    }
                }
        ) {
            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                Checkbox(
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary),
                    checked = isItemChecked,
                    onCheckedChange = { isChecked ->
                        isItemChecked = isChecked
                        if (isChecked) {
                            onItemChecked.invoke(readLaterList.collectionId)
                        } else {
                            onItemUnchecked.invoke(readLaterList.collectionId)
                        }
                    },
                )
            }


            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = readLaterList.collectionTitle,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}

@Composable
@PreviewLightDark
fun ReadingListPreView() {
    val readLaterList = listOf(
        ReadLaterCollection(collectionId = 1, collectionTitle = "Medical Research"),
        ReadLaterCollection(collectionId = 2, collectionTitle = "Tech Research"),
        ReadLaterCollection(collectionId = 3, collectionTitle = "Music"),
        ReadLaterCollection(collectionId = 4, collectionTitle = "Business"),
        ReadLaterCollection(collectionId = 5, collectionTitle = "Health"),
        ReadLaterCollection(collectionId = 6, collectionTitle = "Courses")

    )
    GazetteTheme {
        AddToReadLaterList(
            readLaterList = readLaterList,
            onItemChecked = {},
            onItemUnchecked = {}
        )
    }
}

@Composable
@PreviewLightDark
fun ReadLaterListItemPreview() {
    GazetteTheme {
        AddToReadLaterItem(
            onItemChecked = {},
            onItemUnchecked = {},
            readLaterList = ReadLaterCollection(
                collectionId = 1,
                collectionTitle = "Medical Research"
            )
        )
    }
}