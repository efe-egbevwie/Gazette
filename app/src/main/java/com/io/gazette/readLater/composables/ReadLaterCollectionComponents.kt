package com.io.gazette.readLater.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {

        val isItemChecked = remember { mutableStateOf(false) }

        Checkbox(
            colors = CheckboxDefaults.colors(checkedColor = colorResource(id = R.color.colorPrimary)),
            checked = isItemChecked.value,
            onCheckedChange = { isChecked ->
                isItemChecked.value = isChecked
                if (isChecked) {
                    onItemChecked.invoke(readLaterList.collectionId)
                } else {
                    onItemUnchecked.invoke(readLaterList.collectionId)
                }
            },
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = readLaterList.collectionTitle,
            style = MaterialTheme.typography.bodyMedium
        )
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