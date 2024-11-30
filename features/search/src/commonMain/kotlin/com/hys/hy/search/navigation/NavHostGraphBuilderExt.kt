package com.hys.hy.search.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.hys.hy.search.screens.SearchScreen
import kotlinx.serialization.Serializable


@Serializable
object Search

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addSearchScreen(
    onHomeTabClick: () -> Unit = {},
    onSettingTabClick: () -> Unit = {},
    onTodayTabClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    navController: NavHostController
) {
    composable<Search>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        SearchScreen(
            navController
        )
    }
}