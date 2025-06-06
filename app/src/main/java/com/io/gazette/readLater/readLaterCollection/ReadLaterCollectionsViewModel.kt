package com.io.gazette.readLater.readLaterCollection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.gazette.data.repositories.ReadLaterListRepository
import com.io.gazette.domain.models.ReadLaterCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadLaterCollectionsViewModel @Inject constructor(private val readLaterRepository: ReadLaterListRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(ReadLaterCollectionsScreenState())
    val state = _state.asStateFlow()


    fun onEvent(event: ReadLaterCollectionsScreenEvent) {
        when (event) {
            is ReadLaterCollectionsScreenEvent.GetAllReadLaterCollections -> getAllReadLaterCollections()
            is ReadLaterCollectionsScreenEvent.DeleteReadLaterCollection -> deleteReadLaterCollection(
                collectionId = event.collectionId
            )
        }
    }

    private fun getAllReadLaterCollections() {
        viewModelScope.launch {
            updateScreenState(isLoading = true)
            readLaterRepository.getReadLaterCollectionsAndInfo().collect { collections ->
                updateScreenState(isLoading = false, readLaterCollections = collections)
            }
        }

    }

    private fun deleteReadLaterCollection(collectionId: Int) {
        viewModelScope.launch {
            readLaterRepository.deleteReadLaterCollection(collectionId)
        }
    }


    private fun updateScreenState(
        isLoading: Boolean? = null,
        readLaterCollections: List<ReadLaterCollection>? = null
    ) {
        _state.update { currentState ->
            if (isLoading != null) currentState.copy(isLoading = isLoading) else currentState
            if (readLaterCollections?.isNotEmpty() == true)
                currentState.copy(readLaterCollections = readLaterCollections) else currentState
        }
    }

}


sealed class ReadLaterCollectionsScreenEvent {
    object GetAllReadLaterCollections : ReadLaterCollectionsScreenEvent()
    data class DeleteReadLaterCollection(val collectionId: Int) : ReadLaterCollectionsScreenEvent()

}