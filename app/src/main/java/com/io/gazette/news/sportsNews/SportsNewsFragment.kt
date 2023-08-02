package com.io.gazette.news.sportsNews

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
import com.io.gazette.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportsNewsFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sports_news, container, false).apply {
            findViewById<ComposeView>(R.id.sports_news_compose_view).apply {
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                        viewLifecycleOwner
                    )
                )

                setContent { SportsNews() }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSportsNews()
    }

    @Composable
    fun SportsNews() {
        val state = viewModel.sportsNewsScreenState.collectAsState()

        SportsNewsContent(
            isLoading = state.value.isLoading,
            sportsNews = state.value.sportsNews
        )

    }

    @Composable
    fun SportsNewsContent(
        isLoading: Boolean = false,
        sportsNews: List<NewsItem> = emptyList()
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            if (sportsNews.isNotEmpty()) {
                NewsList(
                    newsItems = sportsNews,
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
}