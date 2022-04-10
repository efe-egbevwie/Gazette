package com.io.gazette.utils

import android.view.View
import androidx.core.view.isVisible

fun View.visible()= run { visibility = View.VISIBLE }

fun View.invisible() = run { visibility = View.INVISIBLE }