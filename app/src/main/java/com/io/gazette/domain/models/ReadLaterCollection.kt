package com.io.gazette.domain.models

data class ReadLaterCollection(
    val collectionTitle: String,
    val collectionId: Int,
    val storyCount: Int = 0
)
