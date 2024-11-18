package com.hys.hy.today.screens


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.animation.SinkAnimateScope
import com.hys.hy.designsystem.component.timeline.TimeLineNodeDefaults
import com.hys.hy.designsystem.component.timeline.TimeLineNodePositionType
import com.hys.hy.designsystem.component.timeline.TimelineNode
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.designsystem.component.toolbars.NavigationBottomBar
import com.hys.hy.designsystem.component.toolbars.TodayTabIndex
import com.hys.hy.today.component.DayScrollableSelector
import com.hys.hy.today.component.MonthSelector
import com.hys.hy.today.component.brush
import com.hys.hy.today.component.color
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

    LaunchedEffect(Unit) {
        viewModel.sendEvent(
            TodayScreenViewModel.TodayEvent.GetTaskByUserAndDate(
                "test",
                state.currentSelectLocalDate
            )
        )
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
                }
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

                    Spacer(modifier = Modifier.height(14.dp))

                    if (state.currentTaskItemList.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                "今天还没有任务",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else {
                        TimeLineColumn(state, viewModel)
                    }
                }

                if (state.isOpenBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            viewModel.sendEvent(
                                TodayScreenViewModel.TodayEvent.CloseBottomSheet
                            )
                        }
                    ) {
                        Row {
                            Button(onClick = {
                                viewModel.sendEvent(
                                    TodayScreenViewModel.TodayEvent.CloseBottomSheet
                                )
                            }) {
                                Text("关闭")
                            }
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun TimeLineColumn(
    state: TodayScreenViewModel.TodayState,
    viewModel: TodayScreenViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(state.currentDayItemListSorted.size) { index ->
            val task = state.currentDayItemListSorted[index]

            TimelineNode(
                circleParameters = TimeLineNodeDefaults.circleParameters(
                    backgroundColor = task.color
                ),
                lineParameters = TimeLineNodeDefaults.linearGradient(
                    startColor = task.color,
                    endColor = state.currentDayItemListSorted[minOf(
                        index + 1,
                        state.currentDayItemListSorted.size - 1
                    )].color
                ),
                timeLineNodePositionType = if (index == state.currentDayItemListSorted.size - 1) {
                    TimeLineNodePositionType.END
                } else {
                    TimeLineNodePositionType.MIDDLE
                }
            ) { modifier ->

                Card(
                    modifier = modifier.height(104.dp).fillMaxWidth().clickable {

                    },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .background(brush = task.brush)
                            .padding(horizontal = 16.dp)
                    ) {

                        Spacer(modifier = Modifier.height(13.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = task.taskTitle,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            FilterChip(
                                selected = task.isDone,
                                onClick = {
                                    task.taskId?.let {
                                        viewModel.sendEvent(
                                            TodayScreenViewModel.TodayEvent.ChangeTaskIsDone(
                                                taskId = it,
                                                isDone = !task.isDone
                                            )
                                        )
                                    }
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = task.color,
                                    containerColor = Color.Transparent
                                ),
                                elevation = null,
                                shape = MaterialTheme.shapes.large,
                                label = {
                                    val text = if (task.isDone) {
                                        "完成"
                                    } else {
                                        "待完成"
                                    }
                                    Text(
                                        text = text,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.surface
                                    )
                                },
                                leadingIcon = {
                                    AnimatedVisibility(
                                        visible = task.isDone,
                                        enter = slideInHorizontally() + fadeIn(),
                                        exit = shrinkHorizontally()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Check,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.surface
                                        )
                                    }
                                }
                            )
                        }


                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = task.taskDescription,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(Modifier.weight(1f))


                        val timeText = if (task.taskSelectTime != null) "${
                            task.taskSelectTime?.hour.toString().padStart(2, '0')
                        }:${
                            task.taskSelectTime?.minute.toString().padStart(2, '0')
                        }" else "未定时间"

                        Text(
                            modifier = Modifier.align(Alignment.End),
                            text = timeText,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.End
                        )

                        Spacer(modifier = Modifier.height(15.dp))
                    }

                }
            }
        }

    }
}


