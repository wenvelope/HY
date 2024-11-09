package com.hys.hy.setting.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hys.hy.setting.screens.SettingScreen
import kotlinx.serialization.Serializable

private const val TIME_DURATION = 300

private val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = TIME_DURATION, easing = LinearOutSlowInEasing)
        )
    }

private val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(durationMillis = TIME_DURATION, easing = LinearOutSlowInEasing)
        )
    }

private val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(durationMillis = TIME_DURATION, easing = LinearOutSlowInEasing)
        )
    }

private val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(durationMillis = TIME_DURATION, easing = LinearOutSlowInEasing)
        )
    }

@Serializable
object Setting

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addSettingScreen(
    onHomeTabClick: () -> Unit,
    onSettingTabClick: () -> Unit = {},
    onTodayTabClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope
) {
    composable<Setting>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        SettingScreen(
            onHomeTabClick = onHomeTabClick,
            onSettingTabClick = onSettingTabClick,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            onTodayTabClick = onTodayTabClick
        )
    }
}