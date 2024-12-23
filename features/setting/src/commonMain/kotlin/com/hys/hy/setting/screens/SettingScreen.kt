package com.hys.hy.setting.screens


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.hys.hy.designsystem.component.animation.SinkAnimateScope
import com.hys.hy.designsystem.component.images.UserAvatarImage
import com.hys.hy.designsystem.component.toolbars.NavigationBottomBar
import com.hys.hy.designsystem.component.toolbars.SettingsTabIndex
import com.hys.hy.setting.navigation.About
import com.hys.hy.setting.navigation.Profile
import com.hys.hy.setting.navigation.TaskCategory
import com.hys.hy.setting.viewmodel.SettingScreenViewModel
import com.hys.hy.setting.viewmodel.SettingScreenViewModel.SettingEvent
import hy.features.setting.generated.resources.Res
import hy.features.setting.generated.resources.category
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SettingScreen(
    onHomeTabClick: () -> Unit,
    onSettingTabClick: () -> Unit,
    onTodayTabClick: () -> Unit,
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: SettingScreenViewModel = koinViewModel(
        viewModelStoreOwner = navController.previousBackStackEntry!! as ViewModelStoreOwner
    ),
) {

    val state by viewModel.container.uiStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendEvent(SettingEvent.GetUserInfo)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            with(sharedTransitionScope) {
                NavigationBottomBar(
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState("bottomBar"),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    currentTabIndex = SettingsTabIndex,
                    onSettingTabClick = {
                        onSettingTabClick.invoke()
                    },
                    onHomeTabClick = {
                        onHomeTabClick.invoke()
                    },
                    onTodayTabClick = {
                        onTodayTabClick.invoke()
                    }
                )
            }
        }
    ) { innerPadding ->

        SinkAnimateScope(
            animatedContentScope
        ) { offset ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .offset { offset }
            ) {

                UserBanner(
                    modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
                    userName = state.userName,
                    userDesc = state.userBio
                )

                Spacer(modifier = Modifier.height(16.dp))

                AccountAndSecurityCard(
                    onAccountInfoClick = {
                        navController.navigate(Profile)
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                AppSettingCard(
                    onCategoryClick = {
                        navController.navigate(TaskCategory)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OtherSettingCard(
                    onAboutClick = {
                        navController.navigate(About)
                    }
                )


            }
        }

    }
}


@Composable
fun UserBanner(
    modifier: Modifier,
    userName: String = "友利奈绪",
    userDesc: String = "wo ai chi pao fu"
) {
    Row(
        modifier = modifier
    ) {
        UserAvatarImage(
            modifier = Modifier.padding(start = 24.dp).size(64.dp).clip(CircleShape),
            contentDescription = "avatar"
        )
        Column {
            Text(
                userName,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 24.dp),
                textAlign = TextAlign.Start
            )
            Text(
                userDesc,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 24.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun AccountAndSecurityCard(
    onAccountInfoClick: () -> Unit = {},
    onAccountManageClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "账户与安全",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 16.dp),
            textAlign = TextAlign.Start
        )

        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            SettingItemArrowRight(
                modifier = Modifier.clickable {
                    onAccountInfoClick.invoke()
                },
                headlineContent = {
                    Text(
                        "账号资料",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SettingItemArrowRight(
                modifier = Modifier.clickable {
                    onAccountManageClick.invoke()
                },
                headlineContent = {
                    Text(
                        "账号管理",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    }
}


@Composable
fun OtherSettingCard(
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onHelpFeedbackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "其他",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 16.dp),
            textAlign = TextAlign.Start
        )
        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {


            SettingItemArrowRight(
                modifier = Modifier.clickable {
                    onUserAgreementClick.invoke()
                },
                headlineContent = {
                    Text(
                        "用户协议",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SettingItemArrowRight(
                modifier = Modifier.clickable {
                    onPrivacyPolicyClick.invoke()
                },
                headlineContent = {
                    Text(
                        "隐私政策",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            SettingItemArrowRight(
                modifier = Modifier.clickable {
                    onAboutClick.invoke()
                },
                headlineContent = {
                    Text(
                        "关于",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SettingItemArrowRight(
                modifier = Modifier.clickable {
                    onHelpFeedbackClick.invoke()
                },
                headlineContent = {
                    Text(
                        "帮助与反馈",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            )
        }
    }
}


@Composable
fun AppSettingCard(
    onNotificationClick: () -> Unit = {},
    onCategoryClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            "应用设置",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 16.dp),
            textAlign = TextAlign.Start
        )

        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                SettingItemArrowRight(
                    modifier = Modifier.clickable {
                        onNotificationClick.invoke()
                    },
                    headlineContent = {
                        Text(
                            "通知",
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                SettingItemArrowRight(
                    modifier = Modifier.clickable {
                        onCategoryClick.invoke()
                    },
                    headlineContent = {
                        Text(
                            "任务种类",
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    leadingContent = {
                        Icon(
                            painter = painterResource(Res.drawable.category),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )


            }


        }
    }
}

@Composable
private fun SettingItemArrowRight(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    leadingContent: @Composable (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier,
        headlineContent = headlineContent,
        leadingContent = leadingContent,
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }
    )
}
