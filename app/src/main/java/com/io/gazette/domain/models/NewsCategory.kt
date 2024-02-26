package com.io.gazette.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class NewsCategory : Parcelable {
    WORLD,
    BUSINESS,
    HEALTH,
    SPORTS;

    companion object{
        val allCategories:List<NewsCategory> = NewsCategory.values().toList()
    }
}