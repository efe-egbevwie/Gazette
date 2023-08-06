package com.io.gazette.readLater.addToReadLater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.io.gazette.R
import com.io.gazette.domain.models.ReadLaterList
import com.io.gazette.readLater.composables.ReadingList
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddToReadingListDialogFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<AddToReadLaterViewModel>()
    private val args by navArgs<AddToReadingListDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_to_reading_list_dialog, container, false)
            .apply {
                findViewById<ComposeView>(R.id.reading_list_dialog_compose_view).apply {
                    setViewCompositionStrategy(
                        ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                            viewLifecycleOwner
                        )
                    )

                    setContent {
                        AddToReadingListDialog()
                    }
                }


            }


    }


    @Composable
    fun AddToReadingListDialog() {

        LaunchedEffect(key1 = 1) {
            viewModel.onEvent(ReadLaterListScreenEvent.GetUserReaLaterLists)
        }

        val readingList =
            viewModel.addToReadingListScreenState.collectAsState()


        AddStoryToReadLaterListDialog(
            storyUrl = args.storyUrl,
            readLaterList = readingList.value.userReadingLists,
            onReadLaterListItemChecked = { readLaterListId ->
                viewModel.onEvent(ReadLaterListScreenEvent.SelectReadLaterListForSavingStory(listId = readLaterListId))
            },
            onReadLaterListItemUnchecked = {},
            onCreateNewReadingListClicked = {
                navigateToAddNewReadingListDialog()
            },
            onDoneButtonClicked = { storyUrl ->
                viewModel.onEvent(
                    ReadLaterListScreenEvent.AddStoryToReadLaterLists(
                        storyUrl = storyUrl
                    )
                )
                closeDialog()
            }
        )

    }

    @Composable
    fun AddStoryToReadLaterListDialog(
        storyUrl: String,
        readLaterList: List<ReadLaterList>,
        onReadLaterListItemChecked: (readLaterListId: Int) -> Unit,
        onReadLaterListItemUnchecked: (readLaterListId: Int) -> Unit,
        onCreateNewReadingListClicked: () -> Unit,
        onDoneButtonClicked: (storyUrl: String) -> Unit
    ) {
        Surface {

            Column {
                Row(modifier = Modifier.padding(top = 20.dp)) {
                    Text(
                        text = "Save story",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        color = colorResource(id = R.color.colorPrimary),
                        text = "Done",
                        fontSize = 21.sp,
                        modifier = Modifier
                            .padding(top = 8.dp, end = 16.dp)
                            .clickable {
                                onDoneButtonClicked.invoke(storyUrl)
                            }
                    )


                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Create new Reading List",
                    color = colorResource(id = R.color.colorPrimary),
                    fontSize = 21.sp,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 10.dp)
                        .clickable {
                            onCreateNewReadingListClicked.invoke()
                        }

                )

                Spacer(modifier = Modifier.height(20.dp))


                if (readLaterList.isNotEmpty()) {
                    ReadingList(readLaterList = readLaterList,
                        onItemChecked = { readLaterListId ->
                            onReadLaterListItemChecked.invoke(readLaterListId)
                        },
                        onItemUnchecked = { readLaterListId ->
                            onReadLaterListItemUnchecked.invoke(readLaterListId)
                        }
                    )

                }

            }

        }
    }


    @Composable
    @Preview(device = "id:pixel_6_pro", showSystemUi = true, showBackground = true)
    fun AddToReadingListDialogPreview() {

        val readLaterList = listOf(
            ReadLaterList(listId = 1, listTitle = "Medical Research", isStoryAlreadyInList = true),
            ReadLaterList(listId = 2, listTitle = "Tech Research", isStoryAlreadyInList = false),
            ReadLaterList(listId = 3, listTitle = "Music", isStoryAlreadyInList = true),
            ReadLaterList(listId = 4, listTitle = "Business", isStoryAlreadyInList = true),
            ReadLaterList(listId = 5, listTitle = "Health", isStoryAlreadyInList = false),
            ReadLaterList(listId = 6, listTitle = "Courses", isStoryAlreadyInList = true)

        )

        AddStoryToReadLaterListDialog(
            storyUrl = "",
            readLaterList,
            onReadLaterListItemChecked = { readLaterListId ->
                Timber.i("selected list id: $readLaterListId")
            },
            onReadLaterListItemUnchecked = { readLaterListId ->
                Timber.i("unselected list id: $readLaterListId")

            },
            onCreateNewReadingListClicked = {

            },
            onDoneButtonClicked = {

            }
        )
    }


    private fun navigateToAddNewReadingListDialog() {
        val action =
            AddToReadingListDialogFragmentDirections.actionAddToReadingListDialogFragmentToAddNewReadingListFragment()
        findNavController().navigate(action)
    }

    private fun closeDialog() {
        findNavController().popBackStack()
    }
}