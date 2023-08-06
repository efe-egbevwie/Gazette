package com.io.gazette.readLater.addToReadLater


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.gazette.data.repositories.ReadingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddToReadLaterViewModel @Inject constructor(private val readingListRepository: ReadingListRepository) :
    ViewModel() {


    private val _createNewReadingListScreenState =
        MutableStateFlow(CreateNewReadingListScreensState())

    val createNewReadingListScreensState =
        _createNewReadingListScreenState.asStateFlow()

    private val _addToReadingListScreenState =
        MutableStateFlow(AddToReadingListScreenState())

    val addToReadingListScreenState = _addToReadingListScreenState.asStateFlow()


    init {
        viewModelScope.launch {
            val readLaterLists = readingListRepository.getReadLaterListsAndInfo().collect{
                Timber.i("read later lists: $it")
            }
        }

    }
    fun onEvent(event: ReadLaterListScreenEvent) {
        when (event) {
            is ReadLaterListScreenEvent.CreateNewReadLaterList -> createNewReadingList()
            is ReadLaterListScreenEvent.NewReadLaterListTitleChanged -> updateNewReadingListTitle(
                newListTitle = event.newListTitle
            )

            is ReadLaterListScreenEvent.GetUserReaLaterLists -> getUserReadingLists()
            is ReadLaterListScreenEvent.SelectReadLaterListForSavingStory -> addReadLaterListForSavingStory(
                listId = event.listId
            )

            is ReadLaterListScreenEvent.AddStoryToReadLaterLists ->
                addStoryToReadingLists(storyUrl = event.storyUrl)
        }
    }


    private fun updateNewReadingListTitle(newListTitle: String) {
        _createNewReadingListScreenState.update { currentState ->
            currentState.copy(listTitle = newListTitle)
        }
    }

    private fun createNewReadingList() = viewModelScope.launch {
        val listTitle = _createNewReadingListScreenState.value.listTitle!!
        readingListRepository.addNewReadingList(listTitle)
    }


    private fun getUserReadingLists() {
        viewModelScope.launch {
            readingListRepository.getReadLaterLists().collect { userList ->
                Timber.i("read later list: $userList")
                _addToReadingListScreenState.update { currentState ->
                    currentState.copy(
                        userReadingLists = userList
                    )
                }
            }
        }
    }

    private fun addReadLaterListForSavingStory(listId: Int) {
        _addToReadingListScreenState.value.selectedListIds.add(listId)
    }

    private fun addStoryToReadingLists(storyUrl: String) {
        val readLaterListsToSaveStory = _addToReadingListScreenState.value.selectedListIds

        if (readLaterListsToSaveStory.isEmpty()) return

        viewModelScope.launch {
            readingListRepository.saveStoryToReadingLists(storyUrl, readLaterListsToSaveStory)
        }
    }
}