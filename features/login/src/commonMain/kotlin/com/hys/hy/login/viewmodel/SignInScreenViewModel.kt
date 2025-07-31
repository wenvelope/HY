package com.hys.hy.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hys.hy.login.model.SignInAction
import com.hys.hy.login.model.SignInData
import com.hys.hy.login.stateMachine.SignInStateMachine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignInScreenViewModel(
    private val signInStateMachine: SignInStateMachine,
) : ViewModel() {
    val state =
        signInStateMachine.state.stateIn(
            scope = viewModelScope,
            initialValue = SignInData(),
            started = SharingStarted.Lazily,
        )

    fun dispatch(action: SignInAction) {
        viewModelScope.launch {
            signInStateMachine.dispatch(action)
        }
    }
}
