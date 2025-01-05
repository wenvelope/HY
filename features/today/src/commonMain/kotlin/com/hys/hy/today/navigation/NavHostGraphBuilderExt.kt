package com.hys.hy.today.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
    onPomodoroClick: (String) -> Unit = {},
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
            onPomodoroClick = onPomodoroClick,
            onCreateTaskButtonClick = onCreateTaskButtonClick,
            sharedTransitionScope = sharedTransitionScope
        )
    }
}
