package com.io.gazette.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class NewsCategory : Parcelable {

    object World : NewsCategory()

    object Business : NewsCategory()
    object Health : NewsCategory()
    object Sports : NewsCategory()
}