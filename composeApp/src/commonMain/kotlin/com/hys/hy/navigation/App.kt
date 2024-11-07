package com.hys.hy.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hys.hy.designsystem.theme.HYTheme
import com.hys.hy.home.navigation.Home
import com.hys.hy.home.navigation.addHomeScreen
import com.hys.hy.home.navigation.navigateFromLoginToHome
import com.hys.hy.home.navigation.popUpToHome
import com.hys.hy.login.navigation.addLoginNavGraph
import com.hys.hy.setting.navigation.addSettingScreen


@Composable
fun App() {
    HYTheme {
        HYNavHost()
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HYNavHost() {
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Home
        ) {

            addLoginNavGraph(
                sharedTransitionScope = this@SharedTransitionLayout,
                navController = navController,
                onStartClick = {
                    navController.navigateFromLoginToHome()
                },
            )

            addHomeScreen(
                onSettingTabClick = navController::navigateSettingWithPopUpToHome,
                sharedTransitionScope = this@SharedTransitionLayout
            )

            addSettingScreen(
                onHomeTabClick = navController::popUpToHome,
                sharedTransitionScope = this@SharedTransitionLayout
            )


        }
    }
}



