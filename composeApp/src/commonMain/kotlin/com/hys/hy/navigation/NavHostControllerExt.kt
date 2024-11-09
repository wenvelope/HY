package com.hys.hy.navigation

import androidx.navigation.NavHostController
import com.hys.hy.home.navigation.Home
import com.hys.hy.setting.navigation.Setting
import com.hys.hy.today.navigation.Today

fun NavHostController.navigateSettingWithPopUpToHome() {
    navigate(Setting) {
        popUpTo(Home) {
            inclusive = false
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateTodayWithPopUpToHome() {
    navigate(Today) {
        popUpTo(Home) {
            inclusive = false
        }
        launchSingleTop = true
    }
}