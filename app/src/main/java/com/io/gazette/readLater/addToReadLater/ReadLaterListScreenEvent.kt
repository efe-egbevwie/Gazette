package com.io.gazette.readLater.addToReadLater

sealed class ReadLaterListScreenEvent {
    object CreateNewReadLaterList : ReadLaterListScreenEvent()

    data class NewReadLaterListTitleChanged(val newListTitle: String) : ReadLaterListScreenEvent()

    object GetUserReaLaterLists : ReadLaterListScreenEvent()

    data class AddStoryToReadLaterLists(val storyUrl: String) :
        ReadLaterListScreenEvent()

    data class SelectReadLaterListForSavingStory(val listId: Int) : ReadLaterListScreenEvent()
}
