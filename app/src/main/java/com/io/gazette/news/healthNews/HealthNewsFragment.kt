package com.io.gazette.news.healthNews

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
import androidx.navigation.fragment.findNavController
import com.io.gazette.MainViewModel
import com.io.gazette.R
import com.io.gazette.common.ui.components.NewsList
import com.io.gazette.domain.models.NewsItem
import com.io.gazette.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HealthNewsFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health_news, container, false).apply {
            findViewById<ComposeView>(R.id.health_news_compose_view).apply {

                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                        viewLifecycleOwner
                    )
                )

                setContent { HealthNews() }

            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHealthNews()
    }


    @Composable
    fun HealthNews() {
        val state = viewModel.healthNewsScreenState.collectAsState()

        HealthNewsContent(
            isLoading = state.value.isLoading,
            healthNews = state.value.healthNews
        )
    }

    @Composable
    fun HealthNewsContent(
        isLoading: Boolean = false,
        healthNews: List<NewsItem> = emptyList()
    ) {


        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            if (healthNews.isNotEmpty()) {
                NewsList(
                    newsItems = healthNews,
                    onItemClick = { newsUrl ->
                        navigateToDetailFragment(newsUrl)
                    },
                    onSaveStoryButtonClicked = { storyUrl, storyImageUrl ->
                        navigateToSaveStoryDialog(storyUrl, storyImageUrl)
                    }
                )
            }

        }
    }

    private fun navigateToDetailFragment(url: String, ) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(url)
        findNavController().navigate(action)
    }

    private fun navigateToSaveStoryDialog(storyUrl: String, storyImageUrl: String?) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToAddToReadingListDialogFragment(storyUrl, storyImageUrl)
        findNavController().navigate(action)
    }

}