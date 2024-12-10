package com.hys.hy.home.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
    onTodayTabClick:()->Unit,
    onSearchClick:()->Unit,
    sharedTransitionScope: SharedTransitionScope
) {
    composable<Home>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        HomeScreen(
            onHomeTabClick = onHomeTabClick,
            onSettingTabClick = onSettingTabClick,
            onTodayTabClick = onTodayTabClick,
            onSearchClick = onSearchClick,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable
        )

    }
}


