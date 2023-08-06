package com.io.gazette.readLater.viewReadLater

import com.io.gazette.domain.models.ReadLaterCollection

data class ReadLaterCollectionsScreenState(
    val isLoading:Boolean = false,
    val readLaterCollections:List<ReadLaterCollection> = mutableListOf()
)