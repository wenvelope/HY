package com.hys.hy.setting.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hys.hy.setting.screens.AboutScreen
import com.hys.hy.setting.screens.ProfileScreen
import com.hys.hy.setting.screens.SettingScreen
import com.hys.hy.setting.screens.TaskCategoryScreen
import kotlinx.serialization.Serializable

private const val TIME_DURATION = 300


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

@Serializable
object SettingGraph

@Serializable
object TaskCategory

@Serializable
object About

@Serializable
object Profile

fun NavGraphBuilder.addProfileScreen(
    onBackClick: () -> Unit
) {
    composable<Profile>(
        enterTransition = { slideInHorizontally { it / 3 } + fadeIn() },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        ProfileScreen(
            onBackClick = onBackClick
        )
    }
}

fun NavGraphBuilder.addTaskCategoryScreen(
    onBackClick: () -> Unit
) {
    composable<TaskCategory>(
        enterTransition = { slideInHorizontally { it / 3 } + fadeIn() },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        TaskCategoryScreen(
            onBackClick = onBackClick
        )
    }
}

fun NavGraphBuilder.addAboutScreen(
    onBackClick: () -> Unit
) {
    composable<About>(
        enterTransition = { slideInHorizontally { it / 3 } + fadeIn() },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        AboutScreen(
            onBackClick = onBackClick
        )
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addSettingScreen(
    onHomeTabClick: () -> Unit,
    onSettingTabClick: () -> Unit = {},
    onTodayTabClick: () -> Unit = {},
    navController: NavController,
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
            navController = navController,
            onTodayTabClick = onTodayTabClick
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addSettingGraph(
    sharedTransitionScope: SharedTransitionScope,
    navController: NavController,
    onHomeTabClick: () -> Unit,
    onTodayTabClick: () -> Unit,
    onSettingTabClick: () -> Unit = {},
) {
    navigation<SettingGraph>(
        startDestination = Setting
    ) {
        addSettingScreen(
            onHomeTabClick = onHomeTabClick,
            onSettingTabClick = onSettingTabClick,
            onTodayTabClick = onTodayTabClick,
            sharedTransitionScope = sharedTransitionScope,
            navController = navController
        )

        addTaskCategoryScreen {
            navController.popBackStack()
        }

        addAboutScreen {
            navController.popBackStack()
        }

        addProfileScreen {
            navController.popBackStack()
        }
    }
}