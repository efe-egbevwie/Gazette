package com.io.gazette.readLater.viewReadLater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.io.gazette.R
import com.io.gazette.common.ui.Pixel6APreview
import com.io.gazette.domain.models.ReadLaterCollection
import com.io.gazette.readLater.composables.ReadLaterCollectionsList
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReadLaterCollectionsFragment : Fragment() {

    private val viewModel by viewModels<ReadLaterCollectionsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_later, container, false).apply {
            findViewById<ComposeView>(R.id.read_later_collections_compose_view).apply {
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                        viewLifecycleOwner
                    )
                )

                setContent {
                    ReadLaterCollectionsScreen()
                }
            }


        }
    }

    @Composable
    fun ReadLaterCollectionsScreen() {
        LaunchedEffect(key1 = 1) {
            viewModel.onEvent(ReadLaterCollectionsScreenEvent.GetAllReadLaterCollections)
        }

        val state = viewModel.state.collectAsState()

        ReadLaterCollectionsScreenContent(
            readLaterCollections = state.value.readLaterCollections,
            onCollectionClicked = {},
            onDeleteCollectionClicked = {}
        )
    }

    @Composable
    fun ReadLaterCollectionsScreenContent(
        readLaterCollections: List<ReadLaterCollection>,
        onCollectionClicked: (collectionId: Int) -> Unit,
        onDeleteCollectionClicked: (collectionId: Int) -> Unit
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                Text(
                    text = "Read Later Collections",
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    color = colorResource(id = R.color.colorPrimary),
                    modifier = Modifier.padding(start = 16.dp, top = 20.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                ReadLaterCollectionsList(
                    readLaterCollections = readLaterCollections,
                    onCollectionItemClicked = onCollectionClicked,
                    onDeleteCollectionClicked = onDeleteCollectionClicked
                )
            }
        }
    }


    @Pixel6APreview
    @Composable
    fun ReadLaterCollectionsScreenPreview() {

        val previewReadLaterCollections = listOf(
            ReadLaterCollection(
                listName = "Medical Research",
                listId = 3,
                storyCount = 7
            ),
            ReadLaterCollection(
                listName = "Music",
                listId = 4,
                storyCount = 1
            ),
            ReadLaterCollection(
                listName = "BasketBall",
                listId = 5,
                storyCount = 20
            ),
            ReadLaterCollection(
                listName = "Health",
                listId = 6,
                storyCount = 9
            ),
            ReadLaterCollection(
                listName = "Javascript",
                listId = 1,
                storyCount = 23
            )
        )

        ReadLaterCollectionsScreenContent(
            readLaterCollections = previewReadLaterCollections,
            onCollectionClicked = {},
            onDeleteCollectionClicked = {}
        )
    }

}