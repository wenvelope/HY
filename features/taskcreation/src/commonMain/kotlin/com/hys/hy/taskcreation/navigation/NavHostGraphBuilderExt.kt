package com.hys.hy.taskcreation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hys.hy.taskcreation.screens.TaskCreationScreen
import kotlinx.serialization.Serializable


@Serializable
data class TaskCreation(
    val taskId: String? = null,
)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addTaskCreationScreen(
    sharedTransitionScope: SharedTransitionScope,
    onBackButtonClick: () -> Unit
) {
    composable<TaskCreation>(
        enterTransition = enterTransition,
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) { backStackEntry ->
        val taskCreationRoute = backStackEntry.toRoute<TaskCreation>()
        TaskCreationScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            onBackButtonClick = onBackButtonClick,
            taskId = taskCreationRoute.taskId
        )
    }
}

private const val TIME_DURATION = 250

private val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = TIME_DURATION, easing = FastOutSlowInEasing)
        )
    }