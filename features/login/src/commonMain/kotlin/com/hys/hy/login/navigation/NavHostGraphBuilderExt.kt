package com.hys.hy.login.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
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
    onSignUpClick: () -> Unit
) {
    composable<SignIn> {
        SignInScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            onBackClick = {},
            onWechatClick = {},
            onPhoneClick = {},
            onEmailLogInClick = {},
            onForgetPwdClick = {},
            onSignUpClick = onSignUpClick
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
    composable<Welcome> {
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
            onSignUpClick = navController::navigateSignUp
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


private const val TIME_DURATION = 300

private val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = TIME_DURATION, easing = LinearOutSlowInEasing)
        )
    }

private val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutHorizontally(
            targetOffsetX = { -it / 3 },
            animationSpec = tween(durationMillis = TIME_DURATION, easing = LinearOutSlowInEasing)
        )
    }

private val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideInHorizontally(
            initialOffsetX = { -it / 3 },
            animationSpec = tween(durationMillis = TIME_DURATION, easing = LinearOutSlowInEasing)
        )
    }

private val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(durationMillis = TIME_DURATION, easing = LinearOutSlowInEasing)
        )
    }