package com.io.gazette.readLater.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.io.gazette.R
import com.io.gazette.domain.models.ReadLaterCollection
import timber.log.Timber

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
    Surface {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .height(60.dp)
        ) {

            val isItemChecked = remember { mutableStateOf(false) }

            Checkbox(
                colors = CheckboxDefaults.colors(checkedColor = colorResource(id = R.color.colorPrimary)),
                checked = isItemChecked.value,
                onCheckedChange = { isChecked ->
                    Timber.i("item checked: $isChecked")
                    isItemChecked.value = isChecked
                    if (isChecked) {
                        onItemChecked.invoke(readLaterList.collectionId)
                    } else {
                        onItemUnchecked.invoke(readLaterList.collectionId)
                    }
                },
                modifier = Modifier
                    .padding(start = 8.dp)


            )


            Text(
                modifier = Modifier
                    .padding(),
                text = readLaterList.collectionTitle,
                fontSize = 17.sp
            )


        }

    }
}

@Composable
@Preview(device = "id:pixel_6a", showSystemUi = true, showBackground = true)
fun ReadingListPreView() {
    val readLaterList = listOf(
        ReadLaterCollection(collectionId = 1, collectionTitle = "Medical Research"),
        ReadLaterCollection(collectionId = 2, collectionTitle = "Tech Research"),
        ReadLaterCollection(collectionId = 3, collectionTitle = "Music"),
        ReadLaterCollection(collectionId = 4, collectionTitle = "Business"),
        ReadLaterCollection(collectionId = 5, collectionTitle = "Health"),
        ReadLaterCollection(collectionId = 6, collectionTitle = "Courses")

    )

    AddToReadLaterList(
        readLaterList = readLaterList,
        onItemChecked = {},
        onItemUnchecked = {}
    )
}

@Composable
@Preview
fun ReadLaterListItemPreview() {
    AddToReadLaterItem(
        onItemChecked = {
            Timber.i("selected id is $it")
        },
        onItemUnchecked = {
            Timber.i("unselected id is $it")
        },
        readLaterList = ReadLaterCollection(
            collectionId = 1,
            collectionTitle = "Medical Research"
        )
    )
}