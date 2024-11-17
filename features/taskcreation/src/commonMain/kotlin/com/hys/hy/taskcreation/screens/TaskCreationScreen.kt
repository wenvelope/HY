package com.hys.hy.taskcreation.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.hys.hy.dateutil.DateTimeUtil
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.task.entities.TaskImportance
import com.hys.hy.task.entities.TaskImportanceName
import com.hys.hy.taskcreation.viewmodel.TaskCreationViewModel
import hy.features.taskcreation.generated.resources.Res
import hy.features.taskcreation.generated.resources.icon_importance
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun TaskCreationScreen(
    viewModel: TaskCreationViewModel = koinViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackButtonClick: () -> Unit
) {
    val state by viewModel.container.uiStateFlow.collectAsState()

    val datePickerState = rememberDatePickerState()

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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp)
        ) {

            FlowRow {
                repeat(3) {
                    FilterChip(
                        selected = false,
                        label = {
                            Text("生活")
                        },
                        onClick = {

                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
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
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                decorationBox = { innerTextField ->
                    innerTextField()
                    if (state.taskTitle.isEmpty()) {
                        Text(
                            "准备做什么？", style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
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

            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.icon_importance),
                    contentDescription = "重要性"
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

        }


    }

}

/**
 * 日期选择组件
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DateSelector(
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

