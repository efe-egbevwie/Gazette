package com.io.gazette.readLater.addToReadLater


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.gazette.data.repositories.ReadLaterListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddToReadLaterViewModel @Inject constructor(private val readLaterListRepository: ReadLaterListRepository) :
    ViewModel() {


    private val _createNewReadingListScreenState =
        MutableStateFlow(CreateNewReadLaterCollectionScreenState())

    val createNewReadingListScreensState =
        _createNewReadingListScreenState.asStateFlow()

    private val _addToReadingListScreenState =
        MutableStateFlow(AddToReadLaterCollectionScreenState())

    val addToReadLaterCollectionScreenState = _addToReadingListScreenState.asStateFlow()


    init {
        viewModelScope.launch {
            val readLaterLists = readLaterListRepository.getReadLaterCollectionsAndInfo().collect {
                Timber.i("read later lists: $it")
            }
        }

    }

    fun onEvent(event: ReadLaterCollectionScreenEvent) {
        when (event) {
            is ReadLaterCollectionScreenEvent.CreateNewReadLaterList -> createNewReadingList()
            is ReadLaterCollectionScreenEvent.NewReadLaterListTitleChanged -> updateNewReadingListTitle(
                newListTitle = event.newListTitle
            )

            is ReadLaterCollectionScreenEvent.GetUserReaLaterLists -> getUserReadingLists()
            is ReadLaterCollectionScreenEvent.SelectReadLaterListForSavingStory -> addReadLaterListForSavingStory(
                listId = event.listId
            )

            is ReadLaterCollectionScreenEvent.AddStoryToReadLaterLists ->
                addStoryToReadingLists(
                    storyUrl = event.storyUrl,
                    storyImageUrl = event.storyImageUrlString
                )
        }
    }


    private fun updateNewReadingListTitle(newListTitle: String) {
        _createNewReadingListScreenState.update { currentState ->
            currentState.copy(listTitle = newListTitle)
        }
    }

    private fun createNewReadingList() = viewModelScope.launch {
        val listTitle = _createNewReadingListScreenState.value.listTitle!!
        readLaterListRepository.addNewReadLaterCollection(listTitle)
    }


    private fun getUserReadingLists() {
        viewModelScope.launch {
            readLaterListRepository.getReadLaterCollections().collect { userList ->
                Timber.i("read later list: $userList")
                _addToReadingListScreenState.update { currentState ->
                    currentState.copy(
                        userReadLaterCollections = userList
                    )
                }
            }
        }
    }

    private fun addReadLaterListForSavingStory(listId: Int) {
        _addToReadingListScreenState.value.selectedCollectionIds.add(listId)
    }

    private fun addStoryToReadingLists(storyUrl: String, storyImageUrl: String? = null) {
        val readLaterListsToSaveStory = _addToReadingListScreenState.value.selectedCollectionIds

        if (readLaterListsToSaveStory.isEmpty()) return

        viewModelScope.launch {
            readLaterListRepository.saveStoryToReadLaterCollections(
                storyUrl,
                storyImageUrl,
                readLaterListsToSaveStory
            )
        }
    }
}