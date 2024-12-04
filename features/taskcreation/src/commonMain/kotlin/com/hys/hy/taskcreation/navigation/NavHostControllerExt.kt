package com.hys.hy.taskcreation.navigation

import androidx.navigation.NavHostController


fun NavHostController.navigateTaskCreation() {
    navigate(TaskCreation(
        taskId = null
    )) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.navigateTaskEdit(taskId: String) {
    navigate(TaskCreation(
        taskId = taskId
    )) {
        launchSingleTop = true
        restoreState = true
    }
}