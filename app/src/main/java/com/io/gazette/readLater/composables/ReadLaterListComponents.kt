package com.io.gazette.readLater.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import com.io.gazette.domain.models.ReadLaterList
import timber.log.Timber

@Composable
fun ReadingList(
    readLaterList: List<ReadLaterList>,
    modifier: Modifier = Modifier,
    onItemChecked: (readingListId: Int) -> Unit,
    onItemUnchecked: (readingListId: Int) -> Unit
) {
    LazyColumn(modifier) {
        items(readLaterList) { listItem ->
            ReadingListCard(
                onItemChecked = onItemChecked,
                onItemUnchecked = onItemUnchecked,
                readLaterList = listItem
            )
        }
    }
}


@Composable
fun ReadingListCard(
    onItemChecked: (readingListId: Int) -> Unit,
    onItemUnchecked: (readingListId: Int) -> Unit,
    readLaterList: ReadLaterList
) {
    Surface {
        Card(
            elevation = 4.dp,
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
            ) {

                val isItemChecked = remember { mutableStateOf(readLaterList.isStoryAlreadyInList) }

                Checkbox(
                    colors = CheckboxDefaults.colors(checkedColor = colorResource(id = R.color.colorPrimary)),
                    checked = isItemChecked.value,
                    onCheckedChange = { isChecked ->
                        Timber.i("item checked: $isChecked")
                        isItemChecked.value = isChecked
                        if (isChecked) {
                            onItemChecked.invoke(readLaterList.listId)
                        } else {
                            onItemUnchecked.invoke(readLaterList.listId)
                        }
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)


                )


                Text(
                    modifier = Modifier
                        .padding(),
                    text = readLaterList.listTitle,
                    fontSize = 17.sp
                )


            }
        }
    }
}

@Composable
@Preview(device = "id:pixel_6a", showSystemUi = true, showBackground = true)
fun ReadingListPreView() {
    val readLaterList = listOf(
        ReadLaterList(listId = 1, listTitle = "Medical Research", isStoryAlreadyInList = true),
        ReadLaterList(listId = 2, listTitle = "Tech Research", isStoryAlreadyInList = false),
        ReadLaterList(listId = 3, listTitle = "Music", isStoryAlreadyInList = true),
        ReadLaterList(listId = 4, listTitle = "Business", isStoryAlreadyInList = true),
        ReadLaterList(listId = 5, listTitle = "Health", isStoryAlreadyInList = false),
        ReadLaterList(listId = 6, listTitle = "Courses", isStoryAlreadyInList = true)

    )

    ReadingList(
        readLaterList = readLaterList,
        onItemChecked = {},
        onItemUnchecked = {}
    )
}

@Composable
@Preview
fun ReadingListItemPreview() {
    ReadingListCard(
        onItemChecked = {
            Timber.i("selected id is $it")
        },
        onItemUnchecked = {
            Timber.i("unselected id is $it")
        },
        readLaterList = ReadLaterList(listId = 1, listTitle = "Medical Research")
    )
}