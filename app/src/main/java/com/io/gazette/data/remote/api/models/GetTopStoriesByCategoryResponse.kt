package com.io.gazette.data.remote.api.models


import com.io.gazette.data.local.model.NewsEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class GetTopStoriesByCategoryResponse(
    val status: String? = null,
    val copyright: String? = null,
    val section: String? = null,

    @SerialName("last_updated")
    val lastUpdated: String? = null,

    @SerialName("num_results")
    val numResults: Long? = null,

    val results: List<Result>? = null
)

@Serializable
data class Result(
    val section: String? = null,
    val subsection: String? = null,
    val title: String? = null,
    val abstract: String? = null,
    val url: String? = null,
    val uri: String? = null,
    val byline: String? = null,

    @SerialName("updated_date")
    val updatedDate: String? = null,

    @SerialName("created_date")
    val createdDate: String? = null,

    @SerialName("published_date")
    val publishedDate: String? = null,

    val multimedia: List<Multimedia>? = null,

    @SerialName("short_url")
    val shortURL: String? = null
)

@Serializable
data class Multimedia(
    val url: String? = null,
    val height: Long? = null,
    val width: Long? = null,
    val caption: String? = null,
    val copyright: String? = null
)

/**
 * Maps a news result item from the NYT API to a NewsEntity.
 * @param fallBackSection: A section to save the news item with in case  the API returns no section
 *
 * */
fun Result.toNewsEntity(fallBackSection: String): NewsEntity {

    var sectionToStore = this.section

    if (this.section.isNullOrBlank()) {
        sectionToStore = fallBackSection
    }

    return NewsEntity(
        title = title.orEmpty(),
        abstract = abstract.orEmpty(),
        url = url.orEmpty(),
        section = sectionToStore.orEmpty(),
        photoUrl = multimedia?.find { it.url != null }?.url.orEmpty(),
        writer = byline.orEmpty(),
        publishedDate = OffsetDateTime.parse(createdDate).toLocalDateTime()
    )
}