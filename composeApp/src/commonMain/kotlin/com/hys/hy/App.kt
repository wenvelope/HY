package com.hys.hy

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hys.hy.screens.SignInScreen
import com.hys.hy.screens.SignUpScreen
import com.hys.hy.theme.HYTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
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
                SignInScreen (
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                ){
                    navController.navigate("SignUpScreen")
                }
            }
            composable("SignUpScreen") {
                SignUpScreen (
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                ){
                    navController.navigate("SignInScreen")
                }
            }
        }
    }
}

