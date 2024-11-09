package com.hys.hy.designsystem.component.dayTab

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


data class DayTabItem(
    val title: String
)

val dayTabItems = listOf(
    DayTabItem("Today"),
    DayTabItem("Tomorrow"),
    DayTabItem("Day After Tomorrow")
)

@Composable
fun DayTab() {
    var setSelectedTabIndex by remember { mutableStateOf(0) }
    TabRow(
        selectedTabIndex = setSelectedTabIndex,
        tabs = {
            dayTabItems.forEachIndexed { index, item ->
                Tab(
                    selected = setSelectedTabIndex == index,
                    onClick = { setSelectedTabIndex = index },
                    text = {
                        Text(item.title)
                    }
                )

            }
        }
    )
}

@Preview
@Composable
fun DayTabPreview() {
    DayTab()
}