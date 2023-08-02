package com.io.gazette.domain.models

data class ReadLaterList(
    val listId: Int,
    val listTitle: String,
    val isStoryAlreadyInList: Boolean = false
)
