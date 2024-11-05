package com.hys.hy

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hys.hy.designsystem.theme.HYTheme
import com.hys.hy.login.navigation.LoginNavGraph
import com.hys.hy.login.navigation.addLoginNavGraph
import com.hys.hy.setting.navigation.addSettingScreen
import com.hys.hy.setting.navigation.navigateSetting

@Preview
@Composable
fun App() {
    HYTheme {
        Surface {
            HYNavHost()
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HYNavHost() {
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = LoginNavGraph
        ) {
            addLoginNavGraph(
                sharedTransitionScope = this@SharedTransitionLayout,
                navController = navController,
                onStartClick = {
                    navController.navigateSetting()
                },
            )
            addSettingScreen()
        }
    }
}



