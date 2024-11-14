package com.hys.hy.taskcreation.navigation

import androidx.navigation.NavHostController


fun NavHostController.navigateTaskCreation() {
    navigate(TaskCreation) {
        launchSingleTop = true
        restoreState = true
    }
}
