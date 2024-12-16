package com.hys.hy.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.hys.hy.user.usecase.GetUserInfoUseCase
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingScreenViewModel (
    private val getUserInfoUseCase: GetUserInfoUseCase
):
    BaseViewModelCore<SettingScreenViewModel.SettingState, SettingScreenViewModel.SettingEvent>() {
    data class SettingState(
        val userName: String,
        val isLogin: Boolean = false,
        val userBio: String
    ) : UiState

    sealed interface SettingEvent : UiEvent {
        data object NavigateToSetting : SettingEvent
        data object GetUserInfo : SettingEvent
    }



    override fun initialState(): SettingState {
        val settingState = SettingState(
            userName = "",
            userBio = ""
        )
        return settingState
    }

    override suspend fun reduce(container: MutableContainer<SettingState, SettingEvent>) {
        container.apply {
            uiEventFlow.collect {
                when (it) {
                    SettingEvent.NavigateToSetting -> {

                    }

                    SettingEvent.GetUserInfo -> {
                        viewModelScope.launch {
                            val userInfo = withContext(Dispatchers.IO){
                                getUserInfoUseCase.execute(Unit)
                            }
                            userInfo.fold(
                                onSuccess = {
                                    updateState {
                                        copy(
                                            userName = it.nickname?: "还没有名字",
                                            userBio = it.bio?: "写点什么吧"
                                        )
                                    }
                                },
                                onFailure = {
                                    // handle error
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}