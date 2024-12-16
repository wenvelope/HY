package com.hys.hy.login.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateSignUp() {
    this.navigate(SignUp)
}

fun NavHostController.navigateWelcome() {
    this.navigate(Welcome)
}

fun NavHostController.navigateSignIn() {
    this.navigate(SignIn)
}

fun NavHostController.navigateSignInWithPopUpToSignIn() {
    this.navigate(SignIn) {
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
    }
}