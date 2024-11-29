package com.hys.hy.setting.screens


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hys.hy.designsystem.component.animation.SinkAnimateScope
import com.hys.hy.designsystem.component.toolbars.NavigationBottomBar
import com.hys.hy.designsystem.component.toolbars.SettingsTabIndex
import com.hys.hy.setting.navigation.About
import com.hys.hy.setting.navigation.TaskCategory
import com.hys.hy.setting.viewmodel.SettingScreenViewModel
import hy.features.setting.generated.resources.Res
import hy.features.setting.generated.resources.category
import hy.features.setting.generated.resources.naixv
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
    viewModel: SettingScreenViewModel = koinViewModel()
) {
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
                    modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth().clickable {

                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )

                NotificationItem(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {

                        }
                )

                TaskCategorySettingItem(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            navController.navigate(TaskCategory)
                        }
                )

                AboutItem(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            navController.navigate(About)
                        }
                )
            }
        }

    }
}

@Composable
fun TaskCategorySettingItem(
    modifier: Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                "任务种类",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        leadingContent = {
            Icon(
                painter = painterResource(Res.drawable.category),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    )

}


@Composable
fun NotificationItem(
    modifier: Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                "通知",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        leadingContent = {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    )

}

@Composable
fun AboutItem(
    modifier: Modifier
) {
    ListItem(
        modifier = modifier,
        leadingContent = {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        headlineContent = {
            Text(
                "关于",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    )

}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UserBanner(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    Row(
        modifier = modifier
    ) {

        with(sharedTransitionScope) {
            Image(
                painter = painterResource(Res.drawable.naixv),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(start = 24.dp).size(64.dp).clip(CircleShape)
            )
        }

        Column {
            Text(
                "友利奈绪",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 24.dp),
                textAlign = TextAlign.Start
            )
            Text(
                "wo ai chi pao fu",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 24.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }

    }
}
