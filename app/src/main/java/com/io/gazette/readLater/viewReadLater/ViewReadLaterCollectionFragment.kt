package com.io.gazette.readLater.viewReadLater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.io.gazette.common.ui.components.sampleNewsList
import com.io.gazette.common.ui.theme.GazetteTheme
import com.io.gazette.domain.models.NewsItem
import com.io.gazette.readLater.composables.ReadLaterStoriesList
import com.io.gazette.utils.navigateSafelyWithAnimations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewReadLaterCollectionFragment : Fragment() {


    private val viewModel by viewModels<ViewReadLaterViewModel>()

    private val args by navArgs<ViewReadLaterCollectionFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext())
            .apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    GazetteTheme {
                        ViewReadLaterCollectionScreen()
                    }
                }
            }
    }

    @Composable
    private fun ViewReadLaterCollectionScreen() {
        val state: ViewReaLaterState by viewModel.state.collectAsState()
        var showDeleteStoryConfirmationDialog by remember {
            mutableStateOf(false)
        }
        var storyUrlToDelete by remember {
            mutableStateOf("")
        }

        LaunchedEffect(key1 = 1) {
            viewModel.onEvent(ViewReadLaterScreenEvent.GetStoriesInCollection(args.collectionId))
        }
        ViewReadLaterCollectionContent(
            isLoading = state.isLoading,
            newsItems = state.newsItems,
            readLaterCollectionTitle = args.collectionTitle,
            onBookmarkIconClicked = { storyUrl ->
                storyUrlToDelete = storyUrl
                showDeleteStoryConfirmationDialog = true
            },
            onItemClicked = { storyUrl: String ->
                navigateToDetailFragment(storyUrl)
            }
        )

        if (showDeleteStoryConfirmationDialog) {
            ConfirmRemoveStoryDialog {
                viewModel.onEvent(ViewReadLaterScreenEvent.RemoveStoryFromCollection(storyUrl = storyUrlToDelete))
            }
        }
    }


    @Composable
    private fun ViewReadLaterCollectionContent(
        isLoading: Boolean,
        readLaterCollectionTitle: String,
        newsItems: List<NewsItem>,
        onBookmarkIconClicked: (storyUrl: String) -> Unit,
        onItemClicked: (storyUrl: String) -> Unit
    ) {
        Column {
            Text(
                text = readLaterCollectionTitle,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp, top = 20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (isLoading) CircularProgressIndicator() else {
                    ReadLaterStoriesList(
                        modifier = Modifier
                            .fillMaxSize(),
                        newsItems = newsItems,
                        onItemClick = onItemClicked,
                        onBookmarkIconClicked = onBookmarkIconClicked
                    )
                }
            }
        }
    }

    @Composable
    private fun ConfirmRemoveStoryDialog(
        onConfirm: () -> Unit
    ) {
        var isOpened by remember {
            mutableStateOf(true)
        }
        if (!isOpened) return

        AlertDialog(
            title = {
                Text(text = "Delete story from collection?")
            },
            onDismissRequest = { isOpened = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                        isOpened = false
                    }
                ) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { isOpened = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    private fun navigateToDetailFragment(storyUrl: String) {
        val action =
            ViewReadLaterCollectionFragmentDirections.actionViewReadLaterCollectionFragmentToDetailFragment(
                storyUrl
            )
        findNavController().navigateSafelyWithAnimations(action)
    }

    @PreviewLightDark
    @Composable
    fun ViewReadLaterCollectionContentPreview() {
        GazetteTheme {
            ViewReadLaterCollectionContent(
                isLoading = false,
                newsItems = sampleNewsList,
                readLaterCollectionTitle = "Sports",
                onBookmarkIconClicked = {},
                onItemClicked = {}
            )
        }
    }
}


