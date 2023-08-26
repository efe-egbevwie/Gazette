package com.io.gazette.data.local

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

class TimeStampTypeConverters {

    @TypeConverter
    fun fromLocalTimeStamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun toLocalTimeStamp(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }


    @TypeConverter
    fun fromLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun toLocalDate(dateTime: LocalDate?): String? {
        return dateTime?.toString()
    }

}