package com.io.gazette.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "read_later_stories",
    primaryKeys = ["story_url", "read_later_collection_id"],
    foreignKeys = [
        ForeignKey(
            entity = ReadLaterCollectionEntity::class,
            parentColumns = ["collection_id"],
            childColumns = ["read_later_collection_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ReadLaterStoryEntity(
    @ColumnInfo("story_url")
    val storyUrl: String,
    @ColumnInfo("image_url")
    val storyImageUrl: String? = null,
    @ColumnInfo("read_later_collection_id")
    val readLaterCollectionId: Int
)
