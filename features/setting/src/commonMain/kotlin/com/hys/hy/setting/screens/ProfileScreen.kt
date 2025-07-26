package com.hys.hy.setting.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.SingletonImageLoader
import coil3.compose.LocalPlatformContext
import com.hys.hy.designsystem.component.images.UserAvatarImage
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.setting.viewmodel.ProfileScreenViewModel
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = koinViewModel(),
    onLogout: () -> Unit,
    onBackClick: () -> Unit
) {

    val state by viewModel.container.uiStateFlow.collectAsState()

    LaunchedEffect(state.isLogout) {
        if (state.isLogout) {
            onLogout()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(ProfileScreenViewModel.ProfileEvent.GetUserInfo)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "账号资料",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    NavigationBackButton(
                        onBackButtonClick = onBackClick
                    )
                },
                modifier = Modifier.shadow(4.dp)
            )
        },
    ) { innerPadding ->

        val scrollState = rememberScrollState(0)

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(top = 16.dp)
                .padding(innerPadding).verticalScroll(scrollState)
        ) {
            //获取loader 用于清除缓存刷新头像
            val imageLoader = SingletonImageLoader.get(LocalPlatformContext.current)

            // 选择头像
            val launcher = rememberFilePickerLauncher(
                mode = FileKitMode.Single,
                type = FileKitType.Image,
                title = "选择头像"
            ) { file ->
                file?.let {
                    viewModel.sendEvent(
                        ProfileScreenViewModel.ProfileEvent.ChangeAvatar(
                            it,
                            imageLoader
                        )
                    )
                }
            }


            ProfileItem(
                modifier = Modifier.clickable {
                    launcher.launch()
                },
                headlineContent = {
                    Text(
                        text = "头像",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                trailingContent = {
                    key(
                        state.avatarRefreshTimes
                    ) {
                        UserAvatarImage(
                            modifier = Modifier.size(64.dp).clip(CircleShape)
                        )
                    }

                }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
            viewModel.profileDialogSettingKeys.forEach { title ->
                ProfileItem(
                    modifier = Modifier.height(40.dp).clickable {
                        viewModel.sendEvent(
                            ProfileScreenViewModel.ProfileEvent.ShowDialog(
                                true,
                                title
                            )
                        )
                    },
                    headlineContent = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    trailingContent = {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = state.getValueByKey(title),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                )
                if (viewModel.profileDialogSettingKeys.last() != title) {
                    HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                onClick = {
                    viewModel.sendEvent(ProfileScreenViewModel.ProfileEvent.Logout)
                }
            ) {
                Text("退出登录")
            }


            if (state.isDialogShow) {
                // Show dialog
                AlertDialog(
                    onDismissRequest = {
                        viewModel.sendEvent(
                            ProfileScreenViewModel.ProfileEvent.ShowDialog(
                                false,
                                state.currentDialogTitle
                            )
                        )
                    },
                    title = {
                        Text("修改${state.currentDialogTitle}")
                    },
                    text = {
                        TextField(
                            value = state.currentDialogTextValue,
                            onValueChange = {
                                viewModel.sendEvent(
                                    ProfileScreenViewModel.ProfileEvent.ChangeDialogTextValue(
                                        it
                                    )
                                )
                            }
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.sendEvent(
                                    ProfileScreenViewModel.ProfileEvent.ChangeValueByKey(
                                        state.currentDialogTitle,
                                        state.currentDialogTextValue
                                    )
                                )
                                viewModel.sendEvent(
                                    ProfileScreenViewModel.ProfileEvent.ShowDialog(
                                        false,
                                        state.currentDialogTitle
                                    )
                                )
                            }
                        ) {
                            Text("确认")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                viewModel.sendEvent(
                                    ProfileScreenViewModel.ProfileEvent.ShowDialog(
                                        false,
                                        state.currentDialogTitle
                                    )
                                )
                            }
                        ) {
                            Text("取消")
                        }
                    }
                )
            }

        }
    }
}


@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    headlineContent: (@Composable () -> Unit),
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable RowScope.() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier,
        headlineContent = headlineContent,
        leadingContent = leadingContent,
        trailingContent = {
            Row {
                trailingContent?.invoke(this)
                Spacer(modifier = Modifier.size(16.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    contentDescription = null
                )
            }

        }
    )

}