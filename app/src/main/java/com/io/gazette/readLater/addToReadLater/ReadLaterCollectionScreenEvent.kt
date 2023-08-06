package com.io.gazette.readLater.addToReadLater

sealed class ReadLaterCollectionScreenEvent {
    object CreateNewReadLaterList : ReadLaterCollectionScreenEvent()

    data class NewReadLaterListTitleChanged(val newListTitle: String) : ReadLaterCollectionScreenEvent()

    object GetUserReaLaterLists : ReadLaterCollectionScreenEvent()

    data class AddStoryToReadLaterLists(val storyUrl: String) :
        ReadLaterCollectionScreenEvent()

    data class SelectReadLaterListForSavingStory(val listId: Int) : ReadLaterCollectionScreenEvent()
}
