package com.hys.hy.login.model

import androidx.compose.material3.SnackbarHostState

data class SignInData(
    val email: String = "",
    val password: String = "",
    val isLogin: Boolean = false,
    val snackBarState: SnackbarHostState = SnackbarHostState(),
)

sealed interface SignInAction {
    data class ChangeEmail(
        val email: String,
    ) : SignInAction

    data class ChangePassword(
        val password: String,
    ) : SignInAction

    data object SignIn : SignInAction

    data object HideTopNavIcon : SignInAction
}
