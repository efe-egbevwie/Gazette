package com.io.gazette.data.remote.api.models


import com.io.gazette.data.local.model.NewsEntity
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
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

    @SerialName("item_type")
    val itemType: ItemType? = null,

    @SerialName("updated_date")
    val updatedDate: String? = null,

    @SerialName("created_date")
    val createdDate: String? = null,

    @SerialName("published_date")
    val publishedDate: String? = null,

    @SerialName("material_type_facet")
    val materialTypeFacet: String? = null,

    val kicker: Kicker? = null,

    @SerialName("des_facet")
    val desFacet: List<String>? = null,

    @SerialName("org_facet")
    val orgFacet: List<String>? = null,

    @SerialName("per_facet")
    val perFacet: List<String>? = null,

    @SerialName("geo_facet")
    val geoFacet: List<String>? = null,

    val multimedia: List<Multimedia>? = null,

    @SerialName("short_url")
    val shortURL: String? = null
)

@Serializable
enum class ItemType(val value: String) {
    Article("Article"),
    Interactive("Interactive"),
    Promo("Promo");

    companion object : KSerializer<ItemType> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor(
                    "com.io.gazette.data.remote.ItemType",
                    PrimitiveKind.STRING
                )
            }

        override fun deserialize(decoder: Decoder): ItemType =
            when (val value = decoder.decodeString()) {
                "Article" -> Article
                "Interactive" -> Interactive
                "Promo" -> Promo
                else -> throw IllegalArgumentException("ItemType could not parse: $value")
            }

        override fun serialize(encoder: Encoder, value: ItemType) {
            return encoder.encodeString(value.value)
        }
    }
}

@Serializable
enum class Kicker(val value: String) {
    Empty(""),
    InCaseYouMissedIt("IN CASE YOU MISSED IT"),
    OutThere("Out There"),
    TheSaturdayProfile("The Saturday Profile");

    companion object : KSerializer<Kicker> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor(
                    "com.io.gazette.data.remote.Kicker",
                    PrimitiveKind.STRING
                )
            }

        override fun deserialize(decoder: Decoder): Kicker =
            when (val value = decoder.decodeString()) {
                "" -> Empty
                "IN CASE YOU MISSED IT" -> InCaseYouMissedIt
                "Out There" -> OutThere
                "The Saturday Profile" -> TheSaturdayProfile
                else -> throw IllegalArgumentException("Kicker could not parse: $value")
            }

        override fun serialize(encoder: Encoder, value: Kicker) {
            return encoder.encodeString(value.value)
        }
    }
}

@Serializable
data class Multimedia(
    val url: String? = null,
    val format: Format? = null,
    val height: Long? = null,
    val width: Long? = null,
    val type: Type? = null,
    val subtype: Subtype? = null,
    val caption: String? = null,
    val copyright: String? = null
)

@Serializable
enum class Format(val value: String) {
    LargeThumbnail("Large Thumbnail"),
    SuperJumbo("Super Jumbo"),
    ThreeByTwoSmallAt2X("threeByTwoSmallAt2X");

    companion object : KSerializer<Format> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor(
                    "com.io.gazette.data.remote.Format",
                    PrimitiveKind.STRING
                )
            }

        override fun deserialize(decoder: Decoder): Format =
            when (val value = decoder.decodeString()) {
                "Large Thumbnail" -> LargeThumbnail
                "Super Jumbo" -> SuperJumbo
                "threeByTwoSmallAt2X" -> ThreeByTwoSmallAt2X
                else -> throw IllegalArgumentException("Format could not parse: $value")
            }

        override fun serialize(encoder: Encoder, value: Format) {
            return encoder.encodeString(value.value)
        }
    }
}

@Serializable
enum class Subtype(val value: String) {
    Photo("photo");

    companion object : KSerializer<Subtype> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor(
                    "com.io.gazette.data.remote.Subtype",
                    PrimitiveKind.STRING
                )
            }

        override fun deserialize(decoder: Decoder): Subtype =
            when (val value = decoder.decodeString()) {
                "photo" -> Photo
                else -> throw IllegalArgumentException("Subtype could not parse: $value")
            }

        override fun serialize(encoder: Encoder, value: Subtype) {
            return encoder.encodeString(value.value)
        }
    }
}

@Serializable
enum class Type(val value: String) {
    Image("image");

    companion object : KSerializer<Type> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor(
                    "com.io.gazette.data.remote.Type",
                    PrimitiveKind.STRING
                )
            }

        override fun deserialize(decoder: Decoder): Type =
            when (val value = decoder.decodeString()) {
                "image" -> Image
                else -> throw IllegalArgumentException("Type could not parse: $value")
            }

        override fun serialize(encoder: Encoder, value: Type) {
            return encoder.encodeString(value.value)
        }
    }
}

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