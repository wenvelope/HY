package com.hys.hy.setting.viewmodel

import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState

class SettingScreenModel :
    BaseViewModelCore<SettingScreenModel.SettingState, SettingScreenModel.SettingEvent>() {
    data class SettingState(
        val userName:String
    ) : UiState

    sealed interface SettingEvent : UiEvent {
        data object NavigateToSetting : SettingEvent
    }

    override fun initialState(): SettingState {
        val settingState = SettingState(
            userName = "友利奈绪"
        )
        return settingState
    }

    override suspend fun reduce(container: MutableContainer<SettingState, SettingEvent>) {
         container.apply {
             uiEventFlow.collect{
                 when(it){
                     SettingEvent.NavigateToSetting -> {

                     }
                 }
             }
         }
    }
}