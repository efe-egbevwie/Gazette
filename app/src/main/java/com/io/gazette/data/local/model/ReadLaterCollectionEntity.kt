package com.io.gazette.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.io.gazette.domain.models.ReadLaterCollection

@Entity(tableName = "read_later_collections")
data class ReadLaterCollectionEntity(
    @ColumnInfo("collection_name")
    val collectionName: String,

    @ColumnInfo("collection_id")
    @PrimaryKey(autoGenerate = true)
    val collectionId: Int = 0,

    )


fun ReadLaterCollectionEntity.toDomainRedLaterCollection(): ReadLaterCollection {
    return ReadLaterCollection(
        collectionTitle = this.collectionName,
        collectionId = this.collectionId,
        storyCount = 0
    )
}