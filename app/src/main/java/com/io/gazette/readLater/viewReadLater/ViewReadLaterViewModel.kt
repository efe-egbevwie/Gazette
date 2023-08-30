package com.io.gazette.readLater.viewReadLater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.gazette.data.repositories.ReadLaterListRepository
import com.io.gazette.domain.models.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewReadLaterViewModel @Inject constructor(private val readLaterRepository: ReadLaterListRepository) :
    ViewModel() {

    var state = MutableStateFlow(ViewReaLaterState())
        private set

    fun onEvent(event: ViewReadLaterScreenEvent) {
        when (event) {
            is ViewReadLaterScreenEvent.GetStoriesInCollection -> getStoriesInReadLaterCollection(
                collectionId = event.collectionId
            )

            is ViewReadLaterScreenEvent.RemoveStoryFromCollection -> removeStoryFromCollection(
                storyUrl = event.storyUrl
            )
        }
    }

    private fun getStoriesInReadLaterCollection(collectionId: Int) {

        state.update { currentState ->
            currentState.copy(isLoading = true)
        }

        viewModelScope.launch {
            readLaterRepository.getStoriesInCollection(collectionId).collect { newsItems ->
                state.update { currentState ->
                    currentState.copy(isLoading = false, newsItems = newsItems)
                }
            }
        }

    }

    private fun removeStoryFromCollection(storyUrl: String) {
        viewModelScope.launch {
            readLaterRepository.deleteStoryFromCollection(storyUrl)
        }
    }


}

data class ViewReaLaterState(
    val isLoading: Boolean = false,
    val newsItems: List<NewsItem> = emptyList()
)

sealed class ViewReadLaterScreenEvent {
    data class GetStoriesInCollection(val collectionId: Int) : ViewReadLaterScreenEvent()

    data class RemoveStoryFromCollection(val storyUrl: String) : ViewReadLaterScreenEvent()

}