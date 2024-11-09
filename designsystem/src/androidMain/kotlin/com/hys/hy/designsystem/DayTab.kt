package com.hys.hy.designsystem

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun DayTabPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        DayTab()
    }
}

data class DayTabItem(
    val title: String
)

val dayTabItems = listOf(
    DayTabItem("Today"),
    DayTabItem("Today"),
    DayTabItem("Today"),
    DayTabItem("Today"),
    DayTabItem("Today"),
    DayTabItem("Today"),
    DayTabItem("Today"),
)

@Composable
fun DayTab() {

    var setSelectedTabIndex by remember { mutableIntStateOf(3) }
    ScrollableTabRow(
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
        },
        divider = {},
        indicator = {
            tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                height = 40.dp,
                modifier = Modifier.tabIndicatorOffset(tabPositions[setSelectedTabIndex])
            )
        }
    )
}