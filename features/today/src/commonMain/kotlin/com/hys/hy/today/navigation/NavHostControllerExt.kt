package com.hys.hy.today.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToday() {
    this.navigate(Today){
        launchSingleTop = true
    }
}

