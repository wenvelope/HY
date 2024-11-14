package com.hys.hy.today.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hys.hy.today.screens.TodayScreen
import kotlinx.serialization.Serializable


@Serializable
object Today

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addTodayScreen(
    onHomeTabClick: () -> Unit,
    onSettingTabClick: () -> Unit = {},
    onTodayTabClick: () -> Unit = {},
    onCreateTaskButtonClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope
) {
    composable<Today>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        TodayScreen(
            onTodayTabClick = onTodayTabClick,
            onHomeTabClick = onHomeTabClick,
            onSettingTabClick = onSettingTabClick,
            animatedContentScope = this@composable,
            onCreateTaskButtonClick = onCreateTaskButtonClick,
            sharedTransitionScope = sharedTransitionScope
        )
    }
}

private const val TIME_DURATION = 250

private val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Down,
            targetOffset = { it / 3 },
            animationSpec = tween(durationMillis = TIME_DURATION, easing = FastOutSlowInEasing)
        )
    }