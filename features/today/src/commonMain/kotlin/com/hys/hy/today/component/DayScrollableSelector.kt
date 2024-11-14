package com.hys.hy.today.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.dayTab.CuteScrollableTabRow
import com.hys.hy.designsystem.component.dayTab.TabRowDefaults
import com.hys.hy.designsystem.component.dayTab.TabRowDefaults.tabIndicatorOffset
import com.hys.hy.today.model.DayItem

@Composable
fun DayScrollableSelector(
    dayItemList: List<DayItem>,
    selectIndex: Int,
    onTabClick: (tabIndex: Int) -> Unit = {}
) {
    CuteScrollableTabRow(
        selectedTabIndex = selectIndex,
        containerColor = MaterialTheme.colorScheme.background,
        tabs = {
            dayItemList.forEachIndexed { index, item ->

                val backgroundColor by animateColorAsState(
                    animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing),
                    targetValue = if (selectIndex == index) Color.Transparent else MaterialTheme.colorScheme.primaryContainer
                )

                Tab(
                    selected = selectIndex == index,
                    onClick = {
                        onTabClick.invoke(index)
                    },
                    modifier = Modifier
                        .width(92.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(43.dp)),
                    // icon 内部没有textPadding
                    icon = {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(70.dp)
                                .clip(RoundedCornerShape(43.dp))
                                .background(
                                    color = backgroundColor
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                item.dayName,
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                item.dayOfWeekName,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }

                    },
                    selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        divider = {},
        indicator = { tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(
                        tabPositions[selectIndex]
                    ),
                width = 70.dp,
                height = 120.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(43.dp)
            )
        }
    )
}