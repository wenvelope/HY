package com.hys.hy.navigation

import androidx.navigation.NavHostController
import com.hys.hy.home.navigation.Home

import com.hys.hy.setting.navigation.Setting

fun NavHostController.navigateSettingWithPopUpToHome() {
    navigate(Setting) {
        popUpTo(Home) {
            inclusive = false
        }
        launchSingleTop = true
    }
}