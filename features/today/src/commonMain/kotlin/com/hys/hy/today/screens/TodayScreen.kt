package com.hys.hy.today.screens


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.animation.SinkAnimateScope
import com.hys.hy.designsystem.component.dayTab.CuteScrollableTabRow
import com.hys.hy.designsystem.component.dayTab.TabRowDefaults
import com.hys.hy.designsystem.component.dayTab.TabRowDefaults.tabIndicatorOffset
import com.hys.hy.designsystem.component.toolbars.NavigationBottomBar
import com.hys.hy.designsystem.component.toolbars.TodayTabIndex


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun TodayScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onHomeTabClick: () -> Unit = {},
    onSettingTabClick: () -> Unit = {},
    onTodayTabClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SinkAnimateScope(
                animatedContentScope
            ) { offset ->
                TopAppBar(
                    modifier = Modifier.offset { offset },
                    title = {

                    },
                    navigationIcon = {
                        OutlinedCard(
                            onClick = { /* doSomething() */ },
                            modifier = Modifier.padding(start = 24.dp).size(56.dp),
                            shape = CircleShape,
                            elevation = CardDefaults.outlinedCardElevation(
                                defaultElevation = 2.dp,
                            )
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "avatar",
                                    modifier = Modifier.size(24.dp).clip(CircleShape)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                )
            }

        },
        bottomBar = {
            with(sharedTransitionScope) {
                NavigationBottomBar(
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState("bottomBar"),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    currentTabIndex = TodayTabIndex,
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
        SinkAnimateScope(animatedContentScope) { offset ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .offset { offset }
            ) {
                DayScrollableSelector()
            }
        }
    }
}

@Composable
fun DayScrollableSelector(
//    dayItemList: List<>
) {
    val items = remember {
        listOf(
            "1",
            "2", "3", "4", "5", "323", "323", "323", "323", "323", "323"
        )
    }

    var selectIndex by remember {
        mutableIntStateOf(0)
    }


    CuteScrollableTabRow(
        selectedTabIndex = selectIndex,
        containerColor = MaterialTheme.colorScheme.background,
        tabs = {
            items.forEachIndexed { index, item ->

                val backgroundColor by animateColorAsState(
                    animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing),
                    targetValue = if (selectIndex == index) Color.Transparent else MaterialTheme.colorScheme.secondaryContainer
                )

                Tab(
                    selected = selectIndex == index,
                    onClick = {
                        selectIndex = index
                    },
                    modifier = Modifier
                        .width(100.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(43.dp)),
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(43.dp))
                                .background(
                                    color = backgroundColor
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                item, modifier = Modifier
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
