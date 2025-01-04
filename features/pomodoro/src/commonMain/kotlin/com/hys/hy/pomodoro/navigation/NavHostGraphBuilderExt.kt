package com.hys.hy.pomodoro.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hys.hy.pomodoro.screen.PomodoroScreen
import kotlinx.serialization.Serializable

@Serializable
data class Pomodoro(
    val taskId: String? = null,
)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addPomodoroScreen(
    sharedTransitionScope: SharedTransitionScope,
    onBackButtonClick:()->Unit
) {
    composable<Pomodoro>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        val pomodoroRoute = it.toRoute<Pomodoro>()
        PomodoroScreen(
            onBackButtonClick = onBackButtonClick,
            taskId = pomodoroRoute.taskId
        )

    }
}


