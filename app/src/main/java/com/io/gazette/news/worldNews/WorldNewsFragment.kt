package com.io.gazette.news.worldNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.io.gazette.MainViewModel
import com.io.gazette.R
import com.io.gazette.common.ui.components.NewsList
import com.io.gazette.domain.models.NewsItem
import com.io.gazette.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorldNewsFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        imageLoader = ImageLoader(requireContext())
            .newBuilder()
            .memoryCache {
                MemoryCache.Builder(requireContext())
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(requireContext().cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()

        lifecycleScope.launch {
            viewModel.worldNewsState.collect {
                it.worldNews?.forEach { newsItem ->
                    cacheImages(newsItem.photoUrl)
                }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world_news, container, false).apply {
            findViewById<ComposeView>(R.id.world_news_compose_view).apply {
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                        viewLifecycleOwner
                    )
                )
                setContent {
                    WorldNews()
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWorldNews()

    }

    @Composable
    fun WorldNews() {
        val state = viewModel.worldNewsState.collectAsState()

        WorldNewsContent(
            isLoading = state.value.isLoadingWorldNews,
            worldNews = state.value.worldNews,
            errorMessage = state.value.error?.getContentIfNotHandled()?.message
        )
    }

    @Composable
    fun WorldNewsContent(
        isLoading: Boolean = false,
        worldNews: List<NewsItem>? = null,
        errorMessage: String? = null
    ) {


        Box(modifier = Modifier.fillMaxSize()) {

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (worldNews?.isNotEmpty() == true) {

                NewsList(
                    newsItems = worldNews,
                    onItemClick = { newsUrl ->
                        navigateToDetailFragment(newsUrl)
                    },
                    onSaveStoryButtonClicked = { storyUrl ->
                        navigateToSaveStoryDialog(storyUrl)
                    }
                )
            }

        }

    }

    private fun navigateToDetailFragment(url: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(url)
        findNavController().navigate(action)
    }

    private fun navigateToSaveStoryDialog(storyUrl: String) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToAddToReadingListDialogFragment(storyUrl)
        findNavController().navigate(action)
    }

    private fun cacheImages(imageUrl: String) {


        val imageRequest = ImageRequest.Builder(requireContext())
            .data(imageUrl)
            .build()
        imageLoader.enqueue(imageRequest)
    }


}