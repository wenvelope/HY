package com.hys.hy.setting.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateSetting() {
    this.navigate(Setting)
}

fun NavHostController.navigateSettingWithPopUpTo() {
    this.navigate(Setting) {
        popUpTo(Setting) {
            inclusive = true
        }
    }
}