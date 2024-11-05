package com.hys.hy.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hys.hy.setting.screens.SettingScreen
import kotlinx.serialization.Serializable


@Serializable
object Setting

fun NavGraphBuilder.addSettingScreen(
){
    composable<Setting>{
        SettingScreen()
    }
}