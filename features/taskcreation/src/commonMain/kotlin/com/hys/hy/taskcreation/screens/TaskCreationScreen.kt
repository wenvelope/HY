package com.hys.hy.taskcreation.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.designsystem.component.dialog.TimePickerDialog
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.task.entities.TaskImportance
import com.hys.hy.task.entities.TaskImportanceName
import com.hys.hy.taskcreation.viewmodel.TaskCreationViewModel
import hy.features.taskcreation.generated.resources.Res
import hy.features.taskcreation.generated.resources.icon_startTime
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


private fun Modifier.animatePlacement(): Modifier = composed {
    val scope = rememberCoroutineScope()
    var targetOffset by remember { mutableStateOf(IntOffset.Zero) }
    var animatable by remember {
        mutableStateOf<Animatable<IntOffset, AnimationVector2D>?>(null)
    }
    this.onPlaced {
        // Calculate the position in the parent layout
        targetOffset = it.positionInParent().round()
    }
        .offset {
            // Animate to the new target offset when alignment changes.
            val anim =
                animatable
                    ?: Animatable(targetOffset, IntOffset.VectorConverter).also {
                        animatable = it
                    }
            if (anim.targetValue != targetOffset) {
                scope.launch {
                    anim.animateTo(targetOffset, spring(stiffness = StiffnessMediumLow))
                }
            }
            // Offset the child in the opposite direction to the targetOffset, and slowly catch
            // up to zero offset via an animation to achieve an overall animated movement.
            animatable?.let { it.value - targetOffset } ?: IntOffset.Zero
        }
}


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class,
    ExperimentalLayoutApi::class, ExperimentalAnimationApi::class
)
@Composable
fun TaskCreationScreen(
    viewModel: TaskCreationViewModel = koinViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackButtonClick: () -> Unit,
) {
    val state by viewModel.container.uiStateFlow.collectAsState()

    val datePickerState = rememberDatePickerState()

    val timePickerState = rememberTimePickerState(
        is24Hour = true
    )


    LaunchedEffect(Unit) {
        viewModel.sendEvent(TaskCreationViewModel.TaskCreationEvent.GetCategories("test"))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "创建代办",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                },
                navigationIcon = {
                    NavigationBackButton(
                        modifier = Modifier.padding(start = 16.dp),
                        onBackButtonClick = onBackButtonClick
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            viewModel.sendEvent(TaskCreationViewModel.TaskCreationEvent.AddTask)
                        }
                    ) {
                        Text("添加", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 72.dp),
                hostState = state.snackBarHostState
            ) { snackbarData ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Snackbar(
                        snackbarData = snackbarData,
                        modifier = Modifier
                            .padding(horizontal = 60.dp)
                            .align(Alignment.TopCenter),
                        shape = MaterialTheme.shapes.large
                    )
                }

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp)
        ) {

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                val sortedList = remember(state.taskCategoryList, state.taskCategoryName) {
                    state.taskCategoryList.sortedByDescending { it.name == state.taskCategoryName }
                }

                for ((index, category) in sortedList.withIndex()) {
                    key(index,category) {
                        FilterChip(
                            modifier = Modifier.animatePlacement(),
                            selected = state.taskCategoryName == category.name,
                            label = {
                                Text(category.name)
                            },
                            onClick = {
                                if (state.taskCategoryName == category.name) {
                                    viewModel.sendEvent(
                                        TaskCreationViewModel.TaskCreationEvent.ChangeSelectedTaskCategory(
                                            null
                                        )
                                    )
                                } else {
                                    viewModel.sendEvent(
                                        TaskCreationViewModel.TaskCreationEvent.ChangeSelectedTaskCategory(
                                            category.name
                                        )
                                    )
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(category.color)
                            ),
                            shape = MaterialTheme.shapes.large
                        )
                    }
                }


            }

            Spacer(modifier = Modifier.height(14.dp))

            BasicTextField(
                value = state.taskTitle,
                onValueChange = {
                    viewModel.sendEvent(
                        TaskCreationViewModel.TaskCreationEvent.ChangeTaskTitle(
                            it
                        )
                    )
                },
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                decorationBox = { innerTextField ->
                    innerTextField()
                    if (state.taskTitle.isEmpty()) {
                        Text(
                            "准备做什么？", style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                maxLines = 2,
                cursorBrush = SolidColor(value = MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(14.dp))

            BasicTextField(
                modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                value = state.taskDescription,
                onValueChange = {
                    viewModel.sendEvent(
                        TaskCreationViewModel.TaskCreationEvent.ChangeTaskDescription(
                            it
                        )
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                decorationBox = { innerTextField ->
                    innerTextField()
                    if (state.taskDescription.isEmpty()) {
                        Text(
                            "添加描述",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                cursorBrush = SolidColor(value = MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(14.dp))

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            DateSelector(
                onTodayClick = {
                    val todayDate = DateTimeUtil.getCurrentDate()
                    viewModel.sendEvent(
                        TaskCreationViewModel.TaskCreationEvent.ChangeTaskSelectedDate(todayDate)
                    )
                },
                onTomorrowClick = {
                    val tomorrowDate = DateTimeUtil.getCurrentDate().plus(1, DateTimeUnit.DAY)
                    viewModel.sendEvent(
                        TaskCreationViewModel.TaskCreationEvent.ChangeTaskSelectedDate(tomorrowDate)
                    )
                },
                onDatePickerClick = {
                    viewModel.sendEvent(
                        TaskCreationViewModel.TaskCreationEvent.ChangeOpenDatePickerDialog(
                            true
                        )
                    )
                },
                onNoDateClick = {
                    viewModel.sendEvent(
                        TaskCreationViewModel.TaskCreationEvent.ChangeTaskSelectedDate(
                            null
                        )
                    )
                },
                state
            )

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            TaskImportanceSelector(state, viewModel)

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            TaskTimeSelector(viewModel, state)

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            // 日期选择弹窗
            if (state.isOpenDatePickerDialog) {
                DatePickerDialog(
                    onDismissRequest = {
                        viewModel.sendEvent(
                            TaskCreationViewModel.TaskCreationEvent.ChangeOpenDatePickerDialog(
                                false
                            )
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let {
                                val selectedDate = Instant.fromEpochMilliseconds(it)
                                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                                viewModel.sendEvent(
                                    TaskCreationViewModel.TaskCreationEvent.ChangeTaskSelectedDate(
                                        selectedDate
                                    )
                                )
                            }
                            viewModel.sendEvent(
                                TaskCreationViewModel.TaskCreationEvent.ChangeOpenDatePickerDialog(
                                    false
                                )
                            )
                        }) {
                            Text("确认")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            viewModel.sendEvent(
                                TaskCreationViewModel.TaskCreationEvent.ChangeOpenDatePickerDialog(
                                    false
                                )
                            )
                        }) { Text("取消") }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                    )
                }

            }

            // 时间选择弹窗
            if (state.isOpenTimePickerDialog) {
                TimePickerDialog(
                    title = "选择时间",
                    onConfirm = {
                        val localTime =
                            LocalTime(hour = timePickerState.hour, timePickerState.minute)
                        viewModel.sendEvent(
                            TaskCreationViewModel.TaskCreationEvent.ChangeTaskSelectedTime(
                                localTime
                            )
                        )
                        viewModel.sendEvent(
                            TaskCreationViewModel.TaskCreationEvent.ChangeOpenTimePickerDialog(
                                false
                            )
                        )
                    },
                    onCancel = {
                        viewModel.sendEvent(
                            TaskCreationViewModel.TaskCreationEvent.ChangeOpenTimePickerDialog(
                                false
                            )
                        )
                    }
                ) {
                    TimePicker(
                        state = timePickerState
                    )
                }

            }

        }


    }

}

@Composable
private fun TaskTimeSelector(
    viewModel: TaskCreationViewModel,
    state: TaskCreationViewModel.TaskCreationState
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(Res.drawable.icon_startTime),
            contentDescription = "开始时间"
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "开始时间",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FilterChip(
            onClick = {
                viewModel.sendEvent(
                    TaskCreationViewModel.TaskCreationEvent.ChangeOpenTimePickerDialog(
                        true
                    )
                )
            },
            label = {
                val localDateText = state.taskSelectedTime?.let {
                    "${it.hour.toString().padStart(2, '0')}:${it.minute.toString().padEnd(2, '0')}"
                }
                Text(
                    localDateText ?: "选择时间",
                    style = MaterialTheme.typography.bodyMedium
                )

            },
            selected = state.taskSelectedTime != null,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "选择时间"
                )
            },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(horizontal = 4.dp)
        )


    }
}

@Composable
private fun TaskImportanceSelector(
    state: TaskCreationViewModel.TaskCreationState,
    viewModel: TaskCreationViewModel
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Rounded.Warning,
            contentDescription = "重要性",
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "重要性",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.padding(8.dp))

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth(),
            space = SegmentedButtonDefaults.BorderWidth
        ) {
            with(TaskImportanceName.CHINESE_TASK_IMPORTANCE_NAMES.names) {
                forEachIndexed { index, label ->
                    SegmentedButton(
                        selected = state.taskImportance.ordinal == index,
                        onClick = {
                            viewModel.sendEvent(
                                TaskCreationViewModel.TaskCreationEvent.ChangeTaskImportance(
                                    TaskImportance.entries[index]
                                )
                            )
                        },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = size
                        )
                    ) {
                        Text(
                            label,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 1.dp)
                        )
                    }
                }
            }
        }

    }
}

/**
 * 日期选择组件
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DateSelector(
    onTodayClick: () -> Unit,
    onTomorrowClick: () -> Unit,
    onDatePickerClick: () -> Unit,
    onNoDateClick: () -> Unit,
    state: TaskCreationViewModel.TaskCreationState
) {
    FlowRow(
        modifier = Modifier.padding(vertical = 4.dp),
    ) {
        FilterChip(
            selected = state.isTodaySelected,
            label = {
                Text(
                    "今天",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = onTodayClick,
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(horizontal = 4.dp)
        )


        FilterChip(
            selected = state.isTomorrowSelected,
            label = {
                Text(
                    "明天",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = onTomorrowClick,
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        FilterChip(
            onClick = onDatePickerClick,
            label = {
                if (state.taskSelectedDate != null && !state.isTomorrowSelected && !state.isTodaySelected) {
                    val localDateText =
                        "${state.taskSelectedDate.month.number}.${state.taskSelectedDate.dayOfMonth}"
                    Text(localDateText)
                } else {
                    Text(
                        "选择日期",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            },
            selected = state.taskSelectedDate != null && !state.isTomorrowSelected && !state.isTodaySelected,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "选择日期"
                )
            },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        FilterChip(
            onClick = onNoDateClick,
            label = {
                val label = if (state.taskSelectedDate == null) {
                    "待办集"
                } else {
                    "未定日期"
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            selected = state.taskSelectedDate == null,
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

    }
}

