package com.hys.hy.setting.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.setting.viewmodel.ProfileScreenViewModel
import hy.features.setting.generated.resources.Res
import hy.features.setting.generated.resources.naixv
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {

    val state by viewModel.container.uiStateFlow.collectAsState()

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

        Column(
            modifier = Modifier.padding(innerPadding).padding(top = 16.dp).background(
                color = MaterialTheme.colorScheme.surfaceDim
            )
        ) {

            Column(
                modifier = Modifier.fillMaxWidth().shadow(
                    elevation = 2.dp
                )
            ) {
                ProfileItem(
                    headlineContent = {
                        Text(
                            text = "头像",
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    trailingContent = {
                        Image(
                            painter = painterResource(Res.drawable.naixv),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
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