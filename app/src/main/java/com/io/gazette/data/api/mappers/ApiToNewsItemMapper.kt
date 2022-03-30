package com.io.gazette.data.api.mappers

import com.io.gazette.data.api.models.Result
import com.io.gazette.domain.models.NewsItem
import com.io.gazette.utils.DateTimeUtils
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime

fun Result.toDomainNewsItem() = NewsItem(
    title = this.title.orEmpty(),
    abstract = this.abstract.orEmpty(),
    url = this.url.orEmpty(),
    section = this.section.orEmpty(),
    photoUrl = this.multimedia?.find { it.url != null }?.url.orEmpty(),
    writer = this.byline.orEmpty()
)