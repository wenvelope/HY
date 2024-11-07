package com.hys.hy.home.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hys.hy.home.screens.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addHomeScreen(
    onHomeTabClick: () -> Unit = {},
    onSettingTabClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope
) {
    composable<Home>(
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = { ExitTransition.None }
    ) {
        HomeScreen(
            onHomeTabClick = onHomeTabClick,
            onSettingTabClick = onSettingTabClick,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable
        )

    }
}


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