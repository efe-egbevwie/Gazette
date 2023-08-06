package com.io.gazette.readLater.addToReadLater

import com.io.gazette.domain.models.ReadLaterCollection

data class CreateNewReadLaterCollectionScreenState(
    val listTitle: String? = null
)


data class AddToReadLaterCollectionScreenState(
    val userReadLaterCollections: List<ReadLaterCollection> = emptyList(),
    val selectedCollectionIds: MutableList<Int> = mutableListOf()
)
