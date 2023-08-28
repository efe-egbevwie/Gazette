package com.io.gazette.home


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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.google.android.material.tabs.TabLayout
import com.io.gazette.HomeViewModel
import com.io.gazette.R
import com.io.gazette.common.ui.components.NewsCategories
import com.io.gazette.common.ui.components.NewsList
import com.io.gazette.common.ui.theme.GazetteTheme
import com.io.gazette.domain.models.NewsCategory
import com.io.gazette.domain.models.NewsItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val viewModel by viewModels<HomeViewModel>()
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
            viewModel.state.collect { state ->
                state.newsContent?.collect { newsItems ->
                    newsItems.forEach { newsItem ->
                        cacheImages(newsItem.photoUrl)
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false).apply {
            findViewById<ComposeView>(R.id.news_content_compose_view).apply {
                setViewCompositionStrategy(
                    ViewCompositionStrategy.Default
                )

                setContent {
                    GazetteTheme {
                        NewsScreen()
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun NewsScreen() {

        LaunchedEffect(key1 = 1) {
            viewModel.getNews(NewsCategory.World)
        }

        val state = viewModel.state.collectAsState()
        val refreshState =
            rememberPullRefreshState(
                refreshing = state.value.isRefreshing,
                onRefresh = {
                    viewModel.refreshNews()
                }
            )


        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(
                        state = refreshState,
                        enabled = true
                    )
            ) {

                Text(
                    text = "Latest Stories",
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 20.dp)
                )

                NewsCategories(
                    onCategorySelected = { category ->
                        viewModel.getNews(category)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))


                if (state.value.isLoading) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    Box(modifier = Modifier) {

                        NewsContent(
                            isLoading = state.value.isLoading,
                            news = state.value.newsContent,
                            errorMessage = state.value.error?.getContentIfNotHandled()?.message,
                            modifier = Modifier.align(Alignment.Center)
                        )

                        PullRefreshIndicator(
                            refreshing = state.value.isRefreshing,
                            refreshState,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )


                    }


                }


            }

        }
    }

    @Composable
    fun NewsContent(
        isLoading: Boolean = false,
        news: Flow<List<NewsItem>>? = null,
        errorMessage: String? = null,
        modifier: Modifier = Modifier
    ) {

        Timber.i("is loading: $isLoading")


        Column(modifier = Modifier.fillMaxSize()) {

            val newsContent = news?.collectAsState(initial = emptyList())

            if (newsContent?.value?.isNotEmpty() == true) {

                NewsList(
                    newsItems = newsContent.value,
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

    private fun navigateToDetailFragment(url: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(url)
        findNavController().navigate(action)
    }

    private fun navigateToSaveStoryDialog(storyUrl: String, storyImageUrl: String? = null) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToAddToReadingListDialogFragment(
                storyUrl,
                storyImageUrl
            )
        findNavController().navigate(action)
    }

    private fun cacheImages(imageUrl: String) {


        val imageRequest = ImageRequest.Builder(requireContext())
            .data(imageUrl)
            .build()
        imageLoader.enqueue(imageRequest)
    }


}