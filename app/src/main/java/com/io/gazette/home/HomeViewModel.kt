package com.io.gazette.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.io.gazette.common.OneTimeEvent
import com.io.gazette.data.repositories.NytRepository
import com.io.gazette.domain.models.NewsCategory
import com.io.gazette.domain.models.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepository: NytRepository) : ViewModel() {
    var state = MutableStateFlow(HomeScreenState())
        private set

    init {
        getNews()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.GetNews -> getNews()
            is HomeScreenEvent.RefreshNews -> refreshNews()
            is HomeScreenEvent.UpdateCategory -> {
                updateCategory(selectedCategory = event.newCategory)
            }
        }
    }

    private fun refreshNews() {
        viewModelScope.launch(context = Dispatchers.IO) {
            setRefreshingState(isRefreshing = true)
            newsRepository.refreshNews()
                .catch { error ->
                    updateErrorState(error as Exception)
                    setRefreshingState(isRefreshing = false)
                }
                .collect { news: List<NewsItem> ->
                    setRefreshingState(isRefreshing = false)
                    updateNewsState(news)
                }
        }
    }

    private fun getNews() {
        viewModelScope.launch(context = Dispatchers.IO) {
            setLoadingState(isLoading = true)
            newsRepository.getNews()
                .catch { error ->
                    updateErrorState(error as Exception)
                    setLoadingState(isLoading = false)
                }
                .collect { news: List<NewsItem> ->
                    setLoadingState(isLoading = false)
                    updateNewsState(news)
                }
        }
    }


    private fun updateNewsState(news: List<NewsItem>) {
        state.update { currentState ->
            currentState.copy(
                newsContent = news,
                isLoading = false,
                isRefreshing = false
            )
        }
    }

    private fun updateErrorState(error: Exception) {
        state.update { currentState ->
            currentState.copy(error = OneTimeEvent(error))
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
        val error: OneTimeEvent<Exception>? = null,
    ) {
        val newsForCurrentCategory: List<NewsItem> get() = newsContent.filter { it.section.lowercase() == selectedCategory.name.lowercase() }
    }
}

sealed class HomeScreenEvent {
    data class GetNews(val category: NewsCategory) : HomeScreenEvent()
    data class UpdateCategory(val newCategory: NewsCategory) : HomeScreenEvent()
    data object RefreshNews : HomeScreenEvent()
}