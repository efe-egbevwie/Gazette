package com.io.gazette.utils


import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateTimeUtils {
    fun parse(dateTimeString: String): LocalDateTime = try {
        LocalDateTime.parse(dateTimeString)
    } catch (e: Exception) {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        LocalDateTime.parse(dateTimeString, dateFormatter)
    }

    fun String.formatDateTimeStamp(): String? {
        return OffsetDateTime.parse(this)
            .format(
                java.time.format.DateTimeFormatter.ofPattern(
                    "EEEE, MMMM d, uuuu, HH:mm a",
                    Locale.ENGLISH
                )
            )
    }
}