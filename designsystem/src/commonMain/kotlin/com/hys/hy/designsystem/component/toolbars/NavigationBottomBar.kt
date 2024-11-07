package com.hys.hy.designsystem.component.toolbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


const val SettingsTabIndex = 1
const val HomeTabIndex = 0

@Composable
fun NavigationBottomBar(
    currentTabIndex: Int,
    onSettingTabClick: () -> Unit,
    onHomeTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            selected = currentTabIndex == HomeTabIndex,
            onClick = onHomeTabClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text("主页")
            }
        )


        NavigationBarItem(
            selected = currentTabIndex == SettingsTabIndex,
            onClick = onSettingTabClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )
            },
            label = {
                Text("设置")
            }
        )
    }
}