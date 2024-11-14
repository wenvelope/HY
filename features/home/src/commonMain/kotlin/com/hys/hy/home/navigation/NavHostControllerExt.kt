package com.hys.hy.home.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateHome() {
    navigate(Home) {
        launchSingleTop = true
    }
}


fun NavHostController.navigateFromLoginToHome() {
    navigate(Home) {
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
        launchSingleTop = true
    }

}

fun NavHostController.popUpToHome() {
    popBackStack(route = Home, inclusive = false, saveState = true)

}
