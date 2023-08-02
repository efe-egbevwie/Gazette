package com.io.gazette.readingList

import com.io.gazette.domain.models.ReadLaterList

data class CreateNewReadingListScreensState(
    val listTitle: String? = null
)


data class AddToReadingListScreenState(
    val userReadingLists: List<ReadLaterList> = emptyList(),
    val selectedListIds: MutableList<Int> = mutableListOf()
)
