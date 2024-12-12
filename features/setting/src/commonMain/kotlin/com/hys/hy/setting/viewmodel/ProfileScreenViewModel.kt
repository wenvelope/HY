package com.hys.hy.setting.viewmodel

import com.hys.hy.preference.AppPreference
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState

class ProfileScreenViewModel(
    private val appPreference: AppPreference
) :
    BaseViewModelCore<ProfileScreenViewModel.ProfileState, ProfileScreenViewModel.ProfileEvent>() {

    val profileDialogSettingKeys = listOf(
        "昵称",
        "简介",
        "性别",
        "学校",
        "邮箱",
    )

    data class ProfileState(
        val name: String,
        val email: String,
        val phone: String,
        val sex: String,
        val school: String,
        val bio: String,
        val isDialogShow: Boolean = false,
        val currentDialogTitle: String = "",
        val currentDialogTextValue: String = ""
    ) : UiState {
        fun getValueByKey(key: String): String {
            return when (key) {
                "昵称" -> name
                "简介" -> bio
                "性别" -> sex
                "学校" -> school
                "邮箱" -> email
                else -> ""
            }
        }
    }

    sealed interface ProfileEvent : UiEvent {
        data class ChangeName(val name: String) : ProfileEvent
        data class ChangeEmail(val email: String) : ProfileEvent
        data class ChangePhone(val phone: String) : ProfileEvent
        data class ChangeBio(val bio: String) : ProfileEvent
        data object GetUserInfo : ProfileEvent
        data class ShowDialog(val isShow: Boolean, val title: String) : ProfileEvent
        data class ChangeValueByKey(val key: String, val value: String) : ProfileEvent
        data class ChangeDialogTextValue(val value: String) : ProfileEvent
    }

    override fun initialState(): ProfileState {
        return ProfileState(
            name = "",
            email = "",
            phone = "",
            bio = "",
            school = "",
            sex = ""
        )
    }

    override suspend fun reduce(container: MutableContainer<ProfileState, ProfileEvent>) {
        container.apply {
            uiEventFlow.collect { event ->
                when (event) {
                    is ProfileEvent.ChangeBio -> {
                        updateState { copy(bio = event.bio) }
                    }

                    is ProfileEvent.ChangeEmail -> {
                        updateState { copy(email = event.email) }
                    }

                    is ProfileEvent.ChangeName -> {
                        updateState { copy(name = event.name) }
                    }

                    is ProfileEvent.ChangePhone -> {
                        updateState {
                            copy(phone = event.phone)
                        }
                    }

                    ProfileEvent.GetUserInfo -> {
                        if (appPreference.isOfflineModeEnabled()) {
                            return@collect
                        }

                    }

                    is ProfileEvent.ShowDialog -> {
                        updateState {
                            copy(
                                isDialogShow = event.isShow,
                                currentDialogTitle = event.title,
                                currentDialogTextValue = if (event.isShow) getValueByKey(event.title) else ""
                            )
                        }
                    }

                    is ProfileEvent.ChangeValueByKey -> {
                        updateState {
                            copy(
                                name = if (event.key == "昵称") event.value else name,
                                bio = if (event.key == "简介") event.value else bio,
                                sex = if (event.key == "性别") event.value else sex,
                                school = if (event.key == "学校") event.value else school,
                                email = if (event.key == "邮箱") event.value else email
                            )
                        }
                    }

                    is ProfileEvent.ChangeDialogTextValue -> {
                        updateState {
                            copy(
                                currentDialogTextValue = event.value
                            )
                        }
                    }
                }
            }
        }

    }
}