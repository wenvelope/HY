package com.hys.hy.login.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
    navigateToHome: () -> Unit,
    onBackClick: () -> Unit
) {
    composable<SignUp> (
        exitTransition = {ExitTransition.None},
        enterTransition = {EnterTransition.None},
        popExitTransition = {ExitTransition.None},
        popEnterTransition = {EnterTransition.None}
    ){
        SignUpScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            navigateToHome = navigateToHome,
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
    navigateToHome: () -> Unit,
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
            navigateToHome = navigateToHome,
            onBackClick = navController::popBackStack,
        )

        addWelcomeScreen(
            onStartClick = navigateToHome
        )

    }
}
