package com.hys.hy.designsystem.component.toolbars


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import hy.designsystem.generated.resources.Res
import hy.designsystem.generated.resources.calender
import hy.designsystem.generated.resources.home
import hy.designsystem.generated.resources.profile
import org.jetbrains.compose.resources.painterResource

const val SettingsTabIndex = 2
const val HomeTabIndex = 0
const val TodayTabIndex = 1

@Composable
fun NavigationBottomBar(
    currentTabIndex: Int,
    onSettingTabClick: () -> Unit,
    onHomeTabClick: () -> Unit,
    onTodayTabClick: () -> Unit,
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
                    painter = painterResource(Res.drawable.home),
                    contentDescription = null,
                )
            },
            label = {
                Text("主页")
            }
        )

        NavigationBarItem(
            selected = currentTabIndex == TodayTabIndex,
            onClick = onTodayTabClick,
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.calender),
                    contentDescription = null
                )
            },
            label = {
                Text("今日")
            }
        )


        NavigationBarItem(
            selected = currentTabIndex == SettingsTabIndex,
            onClick = onSettingTabClick,
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.profile),
                    contentDescription = null
                )
            },
            label = {
                Text("设置")
            }
        )
    }
}