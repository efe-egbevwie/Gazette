package com.io.gazette.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.io.gazette.domain.models.ReadLaterList

@Entity(tableName = "read_later_lists")
data class ReadLaterListEntity(
    @ColumnInfo("list_name")
    val listName: String,

    @ColumnInfo("list_id")
    @PrimaryKey(autoGenerate = true)
    val listId: Int = 0,

    )


fun ReadLaterListEntity.toDomainReadLaterList(): ReadLaterList {
    return ReadLaterList(
        listId = this.listId,
        listTitle = this.listName
    )
}