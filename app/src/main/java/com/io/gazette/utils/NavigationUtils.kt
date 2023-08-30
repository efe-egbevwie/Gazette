package com.io.gazette.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.io.gazette.R

fun NavController.navigateSafelyWithAnimations(direction: NavDirections) {
    val destinationToNavigate = currentDestination?.getAction(direction.actionId)
    if (destinationToNavigate != null) navigate(direction, animationNavOptions)
}

val animationNavOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.from_right)
    .setExitAnim(R.anim.to_left)
    .setPopEnterAnim(R.anim.from_left)
    .setPopExitAnim(R.anim.to_right)
    .build()
