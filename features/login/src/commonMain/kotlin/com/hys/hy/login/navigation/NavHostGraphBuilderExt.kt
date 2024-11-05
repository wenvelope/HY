package com.hys.hy.login.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hys.hy.login.screens.SignInScreen
import com.hys.hy.login.screens.SignUpScreen
import com.hys.hy.login.screens.WelcomeScreen
import kotlinx.serialization.Serializable


@Serializable
object LoginNavGraph

@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
object Welcome


@OptIn(ExperimentalSharedTransitionApi::class)
internal fun NavGraphBuilder.addSignInScreen(
    sharedTransitionScope: SharedTransitionScope,
    onBackClick: () -> Unit
) {
    composable<SignIn> {
        SignInScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            onBackClick = onBackClick
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
internal fun NavGraphBuilder.addSignUpScreen(
    sharedTransitionScope: SharedTransitionScope,
    onCreateAccountClick: () -> Unit,
    onBackClick: () -> Unit
) {
    composable<SignUp> {
        SignUpScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            onCreateAccountClick = onCreateAccountClick,
            onBackClick = onBackClick
        )
    }
}

internal fun NavGraphBuilder.addWelcomeScreen(
    onStartClick: () -> Unit
) {
    composable<Welcome>(
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it -> it * 2 / 3 },
                animationSpec = tween(durationMillis = 100)
            )
        }
    ) {
        WelcomeScreen(
            onStartClick = onStartClick
        )
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addLoginNavGraph(
    navController: NavHostController,
    onStartClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope
) {

    navigation<LoginNavGraph>(
        startDestination = SignIn
    ) {
        addSignInScreen(
            sharedTransitionScope = sharedTransitionScope,
            onBackClick = navController::navigateSignUp
        )

        addSignUpScreen(
            sharedTransitionScope = sharedTransitionScope,
            onCreateAccountClick = navController::navigateWelcome,
            onBackClick = navController::popBackStack
        )

        addWelcomeScreen(
            onStartClick = onStartClick
        )

    }


}