package com.hys.hy.setting.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateSetting() {
    this.navigate(Setting){
        launchSingleTop = true
    }
}

fun NavHostController.navigateSettingWithPopUpTo() {
    this.navigate(Setting) {
        popUpTo(Setting) {
            inclusive = true
        }
    }
}