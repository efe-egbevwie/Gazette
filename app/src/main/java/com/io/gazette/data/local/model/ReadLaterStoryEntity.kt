package com.io.gazette.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "read_later_stories",
    primaryKeys = ["story_url", "read_later_list_id"],
    foreignKeys = [
        ForeignKey(
            entity = ReadLaterListEntity::class,
            parentColumns = ["list_id"],
            childColumns = ["read_later_list_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ReadLaterStoryEntity(
    @ColumnInfo("story_url")
    val storyUrl: String,
    @ColumnInfo("read_later_list_id")
    val readLaterListId: Int
)
