package com.io.gazette.domain.models

data class ReadLaterCollection(
    val collectionTitle: String,
    private val imageUrls: String? = null,
    val collectionId: Int,
    val storyCount: Int = 0
) {
    fun getThreeImageUrls(): List<String>? {
        return imageUrls?.split(',')?.take(3)
    }
}
