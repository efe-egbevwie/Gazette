package com.io.gazette.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.io.gazette.common.OneTimeEvent
import com.io.gazette.data.repositories.NytRepository
import com.io.gazette.domain.models.GetDataResult
import com.io.gazette.domain.models.NewsCategory
import com.io.gazette.domain.models.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepository: NytRepository) : ViewModel() {


    var state = MutableStateFlow(HomeScreenState())
        private set

    init {
        getNews(NewsCategory.WORLD)
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.GetNews -> getNews(category = event.category)
            is HomeScreenEvent.RefreshNews -> refreshNews()
            is HomeScreenEvent.UpdateCategory -> {
                val categoryUpdated = updateCategory(selectedCategory = event.newCategory)
                if (categoryUpdated) getNews(category = event.newCategory)
            }
        }
    }

    private fun refreshNews() {
        viewModelScope.launch {

            setRefreshingState(true)

            when (val newsResult = newsRepository.refreshNews(state.value.selectedCategory)) {
                is GetDataResult.Success -> {
                    Timber.i("new result:${newsResult.data}")
                    updateNewsState(news = newsResult.data)
                }

                is GetDataResult.Failure -> {
                    state.update { currentState ->
                        currentState.copy(
                            error = OneTimeEvent(newsResult.exception)
                        )
                    }

                    setRefreshingState(isRefreshing = false)
                }
            }
        }

    }


    private fun getNews(category: NewsCategory) {
        viewModelScope.launch {

            updateCategory(category)
            setLoadingState(isLoading = true)

            when (val newsResult = newsRepository.getNewsByCategory(category)) {
                is GetDataResult.Success -> {
                    Timber.i("new result:$newsResult")
                    updateNewsState(news = newsResult.data)
                }

                is GetDataResult.Failure -> {

                    setLoadingState(isLoading = false)

                    state.update { currentState ->
                        currentState.copy(error = OneTimeEvent(newsResult.exception))
                    }
                }
            }
        }
    }


    private fun updateNewsState(news: Flow<List<NewsItem>>?) {
        viewModelScope.launch {
            news?.collect { newsList ->
                state.update { currentState ->
                    currentState.copy(
                        newsContent = newsList,
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            }
        }
    }


    private fun updateCategory(selectedCategory: NewsCategory): Boolean {
        if (state.value.selectedCategory == selectedCategory) return false
        state.update { currentState ->
            currentState.copy(selectedCategory = selectedCategory)
        }
        return true
    }


    private fun setLoadingState(isLoading: Boolean) {
        state.update { currentState ->
            currentState.copy(isLoading = isLoading)
        }
    }

    private fun setRefreshingState(isRefreshing: Boolean) {
        state.update { currentState ->
            currentState.copy(isRefreshing = isRefreshing)
        }
    }


    companion object {

        fun factory(newsRepository: NytRepository): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    HomeViewModel(newsRepository)
                }
            }
        }
    }

    data class HomeScreenState(
        val isLoading: Boolean = true,
        val isRefreshing: Boolean = false,
        val selectedCategory: NewsCategory = NewsCategory.WORLD,
        val newsContent: List<NewsItem> = listOf(),
        val newsListState: LazyListState = LazyListState(),
        val error: OneTimeEvent<Exception>? = null
    )


}

sealed class HomeScreenEvent {
    data class GetNews(val category: NewsCategory) : HomeScreenEvent()
    data class UpdateCategory(val newCategory: NewsCategory) : HomeScreenEvent()
    data object RefreshNews : HomeScreenEvent()
}