package com.hys.hy.pomodoro.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigatePomodoro() {
    navigate(Pomodoro) {
        launchSingleTop = true
    }
}


fun NavHostController.navigatePomodoro(taskId: String) {
    navigate(
        Pomodoro(
            taskId = taskId
        )
    ) {
        launchSingleTop = true
    }
}