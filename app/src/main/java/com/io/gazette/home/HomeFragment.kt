package com.io.gazette.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.io.gazette.R
import com.io.gazette.common.ui.components.LoadingScreen
import com.io.gazette.common.ui.components.NewsCategories
import com.io.gazette.common.ui.components.NewsList
import com.io.gazette.common.ui.components.sampleNewsList
import com.io.gazette.common.ui.theme.GazetteTheme
import com.io.gazette.domain.models.NewsCategory
import com.io.gazette.domain.models.NewsItem
import com.io.gazette.utils.navigateSafelyWithAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext())
            .apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    GazetteTheme {
                        Surface {
                            NewsScreen()
                        }
                    }
                }
            }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun NewsScreen() {
        val state: HomeViewModel.HomeScreenState by viewModel.state.collectAsState()
        val refreshState: PullRefreshState =
            rememberPullRefreshState(
                refreshing = state.isRefreshing,
                onRefresh = {
                    viewModel.onEvent(HomeScreenEvent.RefreshNews)
                }
            )

        val newsListState: LazyListState = state.newsListState
        val newsListCanScrollToTop: Boolean by remember {
            derivedStateOf {
                newsListState.firstVisibleItemIndex > 0
            }
        }
        val scope = rememberCoroutineScope()
        fun scrollListToTop() {
            scope.launch {
                state.newsListState.animateScrollToItem(0)
            }
        }

        NewsScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            newsList = state.newsForCurrentCategory,
            isLoadingNews = state.isLoading,
            selectedCategory = state.selectedCategory,
            onCategorySelected = { category: NewsCategory ->
                viewModel.onEvent(event = HomeScreenEvent.UpdateCategory(newCategory = category))
                scrollListToTop()
            },
            onScrollListToTopClicked = {
                scrollListToTop()
            },
            scrollListToTopButtonVisible = newsListCanScrollToTop,
            refreshState = refreshState,
            isRefreshing = state.isRefreshing,
            newsListState = state.newsListState
        )
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun NewsScreenContent(
        modifier: Modifier = Modifier,
        newsList: List<NewsItem>,
        isLoadingNews: Boolean,
        selectedCategory: NewsCategory,
        onCategorySelected: (category: NewsCategory) -> Unit,
        onScrollListToTopClicked: () -> Unit,
        scrollListToTopButtonVisible: Boolean,
        refreshState: PullRefreshState,
        isRefreshing: Boolean,
        newsListState: LazyListState
    ) {

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "Latest Stories",
                style = MaterialTheme.typography.titleLarge
            )

            NewsCategories(
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected
            )

            AnimatedVisibility(visible = isLoadingNews) {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }

            AnimatedVisibility(visible = isLoadingNews.not() && newsList.isNotEmpty()) {
                Box(modifier = Modifier.pullRefresh(state = refreshState, enabled = true)) {
                    NewsContent(
                        news = newsList,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        newsListState = newsListState
                    )


                    if (scrollListToTopButtonVisible) {
                        IconButton(
                            onClick = { onScrollListToTopClicked() }, modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp)
                        ) {
                            Icon(
                                painterResource(id = R.drawable.arrow_up_circle),
                                contentDescription = "Scroll List Upwards",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(48.dp)
                            )
                        }
                    }

                    PullRefreshIndicator(
                        refreshing = isRefreshing,
                        state = refreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }

    @Composable
    fun NewsContent(
        modifier: Modifier = Modifier,
        news: List<NewsItem>,
        newsListState: LazyListState
    ) {
        NewsList(
            modifier = modifier,
            newsItems = news,
            onItemClick = { newsUrl ->
                navigateToDetailFragment(newsUrl)
            },
            onSaveStoryButtonClicked = { storyUrl, storyImageUrl ->
                navigateToSaveStoryDialog(storyUrl, storyImageUrl)
            },
            listState = newsListState
        )
    }

    private fun navigateToDetailFragment(url: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(url)
        findNavController().navigateSafelyWithAnimations(action)
    }

    private fun navigateToSaveStoryDialog(storyUrl: String, storyImageUrl: String? = null) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToAddToReadingListDialogFragment(
                storyUrl,
                storyImageUrl
            )
        findNavController().navigate(action)
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    @PreviewLightDark
    private fun NewScreenContentPreview() {
        GazetteTheme {
            Surface {
                NewsScreenContent(
                    newsList = sampleNewsList,
                    isLoadingNews = false,
                    selectedCategory = NewsCategory.WORLD,
                    onCategorySelected = {},
                    onScrollListToTopClicked = {},
                    scrollListToTopButtonVisible = true,
                    refreshState = rememberPullRefreshState(
                        refreshing = false,
                        onRefresh = { }
                    ),
                    isRefreshing = true,
                    newsListState = rememberLazyListState()
                )
            }
        }
    }
}