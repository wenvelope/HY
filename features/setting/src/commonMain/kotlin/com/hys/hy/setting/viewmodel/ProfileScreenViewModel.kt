package com.hys.hy.setting.viewmodel

import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import coil3.memory.MemoryCache
import com.hys.hy.auth.usecase.LogoutUseCase
import com.hys.hy.preference.AppPreference
import com.hys.hy.user.usecase.GetUserAvatarUseCase
import com.hys.hy.user.usecase.GetUserInfoUseCase
import com.hys.hy.user.usecase.PostUserAvatarUseCase
import com.hys.hy.user.usecase.UpdateUserInfoUseCase
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileScreenViewModel(
    private val appPreference: AppPreference,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getUSerInfoUseCase: GetUserInfoUseCase,
    private val postUserAvatarUseCase: PostUserAvatarUseCase,
    private val getUserAvatarUseCase: GetUserAvatarUseCase
) :
    BaseViewModelCore<ProfileScreenViewModel.ProfileState, ProfileScreenViewModel.ProfileEvent>() {


    val profileDialogSettingKeys = listOf(
        "昵称",
        "简介",
        "性别",
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
        val currentDialogTextValue: String = "",
        val isLogout: Boolean = false,
        val avatarRefreshTimes: Int = 0,
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
        data class ChangeAvatar(val imageFile: PlatformFile, val imageLoader: ImageLoader) :
            ProfileEvent
        data object RefreshAvatar : ProfileEvent
        data object Logout : ProfileEvent
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
                        viewModelScope.launch(Dispatchers.IO) {
                            getUSerInfoUseCase.execute(Unit).fold(
                                onSuccess = { response ->
                                    updateState {
                                        copy(
                                            name = response.nickname ?: "",
                                            bio = response.bio ?: "",
                                        )
                                    }
                                },
                                onFailure = {
                                    println(it.message)
                                }
                            )
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

                        viewModelScope.launch(Dispatchers.IO) {

                            updateUserInfoUseCase.execute(
                                UpdateUserInfoUseCase.Params(
                                    nickname = if (event.key == "昵称") event.value else null,
                                    bio = if (event.key == "简介") event.value else null,
                                )
                            ).fold(
                                onSuccess = { response ->
                                    updateState {
                                        copy(
                                            name = response.nickname ?: "",
                                            bio = response.bio ?: "",
                                        )
                                    }
                                },
                                onFailure = {
                                    println(it.message)
                                }
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

                    ProfileEvent.Logout -> {
                        viewModelScope.launch {
                            launch(Dispatchers.IO) {
                                logoutUseCase.execute(Unit)
                            }
                            appPreference.clearUserTokenAndUserId()
                            appPreference.setOfflineModeEnabled(false)
                            appPreference.setNotFirstUse(isFirstUse = true)
                            updateState {
                                copy(isLogout = true)
                            }
                        }

                    }

                    is ProfileEvent.ChangeAvatar -> {
                        viewModelScope.launch {
                            val result = withContext(Dispatchers.IO) {
                                postUserAvatarUseCase.execute(
                                    PostUserAvatarUseCase.Param(
                                        event.imageFile.readBytes()
                                    )
                                )
                            }
                            result.fold(
                                onSuccess = {
                                    println("上传成功")
                                    event.imageLoader.memoryCache?.remove(
                                        MemoryCache.Key(
                                            getUserAvatarUseCase.execute(Unit).urlString
                                        )
                                    )
                                    event.imageLoader.diskCache?.remove(
                                        getUserAvatarUseCase.execute(Unit).urlString
                                    )
                                    sendEvent(ProfileEvent.RefreshAvatar)
                                },
                                onFailure = {
                                    println("POST" + it.message)
                                }
                            )

                        }
                    }

                    ProfileEvent.RefreshAvatar -> {
                        updateState {
                            copy(avatarRefreshTimes = avatarRefreshTimes + 1)
                        }
                    }
                }
            }
        }
    }



}