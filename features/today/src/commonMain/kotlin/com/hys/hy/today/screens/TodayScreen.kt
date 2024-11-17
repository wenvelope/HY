package com.hys.hy.today.screens


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.animation.SinkAnimateScope
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.designsystem.component.toolbars.NavigationBottomBar
import com.hys.hy.designsystem.component.toolbars.TodayTabIndex
import com.hys.hy.today.component.DayScrollableSelector
import com.hys.hy.today.component.MonthSelector
import com.hys.hy.today.viewmodel.TodayScreenViewModel
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun TodayScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onHomeTabClick: () -> Unit = {},
    onSettingTabClick: () -> Unit = {},
    onTodayTabClick: () -> Unit = {},
    onCreateTaskButtonClick: () -> Unit = {},
    viewModel: TodayScreenViewModel = koinViewModel()
) {

    val state by viewModel.container.uiStateFlow.collectAsState()

    LaunchedEffect(Unit){
        viewModel.sendEvent(TodayScreenViewModel.TodayEvent.GetCurrentDayTasks("test"))
    }

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
                        NavigationBackButton(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {

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

        },
        floatingActionButton = {

                FloatingActionButton(
                    modifier = Modifier,
                    onClick = {
                        onCreateTaskButtonClick.invoke()
                    },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }

        }
    ) { innerPadding ->
        SinkAnimateScope(animatedContentScope) { offset ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .offset { offset }
            ) {

                MonthSelector(
                    onLeftMonthClick = {
                        viewModel.sendEvent(
                            TodayScreenViewModel.TodayEvent.ChangeCurrentSelectMonth(
                                state.forwardMonth
                            )
                        )
                    },
                    onRightMonthClick = {
                        viewModel.sendEvent(
                            TodayScreenViewModel.TodayEvent.ChangeCurrentSelectMonth(
                                state.nextMonth
                            )
                        )
                    },
                    state
                )

                Spacer(modifier = Modifier.height(14.dp))

                DayScrollableSelector(
                    dayItemList = state.currentDayItemList,
                    selectIndex = state.currentSelectDayIndex,
                    onTabClick = { index ->
                        viewModel.sendEvent(
                            TodayScreenViewModel.TodayEvent.ChangeCurrentSelectDay(
                                index
                            )
                        )
                    }
                )

                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        "时间轴",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    if (state.currentTaskItemList.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                "今天还没有任务",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else {
                        TimeLineColumn(state)
                    }

                }


            }
        }
    }
}


@Composable
fun TimeLineColumn(
    state: TodayScreenViewModel.TodayState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(state.currentTaskItemList.size) { index ->
            val task = state.currentTaskItemList[index]
            Text(
                text = task.taskTitle,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

    }
}


