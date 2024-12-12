package com.hys.hy.setting.viewmodel

import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState

class SettingScreenViewModel :
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
            userName = "友利奈绪",
            userBio = "我爱吃泡芙"
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

                    }
                }
            }
        }
    }
}