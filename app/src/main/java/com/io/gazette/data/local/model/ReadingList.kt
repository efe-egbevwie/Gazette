package com.io.gazette.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_list")
data class ReadingList(
    @ColumnInfo("list_name")
    val listName: String,

    @ColumnInfo("list_id")
    @PrimaryKey(autoGenerate = true)
    val listId: Int = 0,

    )