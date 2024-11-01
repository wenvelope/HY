package com.hys.hy

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hys.hy.designsystem.theme.HYTheme
import com.hys.hy.login.screens.SignInScreen
import com.hys.hy.login.screens.SignUpScreen
import com.hys.hy.login.screens.WelcomeScreen



@Composable
fun App() {
    HYTheme {
        Surface {
            SignModule()
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SignModule() {
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "SignInScreen"
        ) {
            composable("SignInScreen") {
                SignInScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                ) {
                    navController.navigate("SignUpScreen")
                }
            }
            composable("SignUpScreen") {
                SignUpScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    onCreateAccountClick = {
                        navController.navigate("WelcomeScreen")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable("WelcomeScreen", enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it -> it * 2 / 3 },
                    animationSpec = tween(durationMillis = 500)
                )
            }) {
                WelcomeScreen()
            }
        }
    }
}

