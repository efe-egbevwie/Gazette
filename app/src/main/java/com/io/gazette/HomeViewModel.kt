package com.io.gazette

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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepository: NytRepository) : ViewModel() {


    var state = MutableStateFlow(HomeScreenState())
        private set


    fun refreshNews() {
        viewModelScope.launch {

            state.update { currentState ->
                currentState.copy(isRefreshing = true)

            }

            when (val newsResult = newsRepository.refreshNews(NewsCategory.World)) {
                is GetDataResult.Success -> {
                    Timber.i("new result:$newsResult")
                    state.update { currentState ->
                        currentState.copy(newsContent = newsResult.data, isRefreshing = false)
                    }
                }

                is GetDataResult.Failure -> {
                    state.update { currentState ->
                        currentState.copy(
                            error = OneTimeEvent(newsResult.exception),
                            isRefreshing = false
                        )
                    }
                }
            }
        }

    }


    fun getNews(category: NewsCategory) {
        viewModelScope.launch {

            state.update { currentState ->
                currentState.copy(isLoading = true)

            }

            when (val newsResult = newsRepository.getNewsByCategory(category)) {
                is GetDataResult.Success -> {
                    Timber.i("new result:$newsResult")
                    state.update { currentState ->
                        currentState.copy(newsContent = newsResult.data, isLoading = false)
                    }
                }

                is GetDataResult.Failure -> {
                    state.update { currentState ->
                        currentState.copy(
                            error = OneTimeEvent(newsResult.exception),
                            isLoading = false
                        )
                    }
                }
            }
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
        val newsContent: Flow<List<NewsItem>>? = flowOf(),
        val error: OneTimeEvent<Exception>? = null
    )

}