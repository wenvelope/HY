package com.hys.hy.setting.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.setting.viewmodel.TaskCategoryScreenViewModel
import com.hys.hy.taskCategory.entities.TaskCategory
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


internal val defaultSupportCategoryColors = listOf(
    Color(0xFF3B82F6),
    Color(0xFFEF4444),
    Color(0xFFEAB308),
    Color(0xFF22C55E),
    Color(0xFF6366F1),
    Color(0xFFA855F7),
)


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TaskCategoryScreen(
    onBackClick: () -> Unit,
    viewModel: TaskCategoryScreenViewModel = koinViewModel()
) {

    val state by viewModel.container.uiStateFlow.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.sendEvent(TaskCategoryScreenViewModel.TaskCategoryEvent.GetTaskCategories("test"))
    }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "任务种类",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    navigationIcon = {
                        NavigationBackButton(
                            onBackButtonClick = onBackClick
                        )
                    }
                )
                HorizontalDivider()
            }

        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .statusBarsPadding(),
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(
                    items = state.taskCategories,
                    key = { _, item -> item.id!! },
                ) { _: Int, item: TaskCategory ->


                    ListItem(
                        modifier = Modifier.clip(MaterialTheme.shapes.large).animateItem(),
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            trailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                        tonalElevation = 2.dp,
                        shadowElevation = 1.dp,
                        headlineContent = {
                            Text(
                                item.name,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 1
                            )
                        },
                        leadingContent = {
                            Box(
                                modifier = Modifier.size(24.dp).clip(CircleShape).background(
                                    color = Color(item.color)
                                )
                            )
                        },
                        trailingContent = {
                            IconButton(
                                onClick = {
                                    viewModel.sendEvent(
                                        TaskCategoryScreenViewModel.TaskCategoryEvent.DeleteTaskCategory(
                                            item.id!!
                                        )
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "删除"
                                )
                            }
                        }
                    )

                }


            }

            ElevatedButton(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .height(56.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                shape = MaterialTheme.shapes.large,
                onClick = {
                    viewModel.sendEvent(
                        TaskCategoryScreenViewModel.TaskCategoryEvent.ShowBottomSheet(
                            true
                        )
                    )
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "添加任务分类",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            if (state.isShowBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        viewModel.sendEvent(
                            TaskCategoryScreenViewModel.TaskCategoryEvent.ShowBottomSheet(
                                false
                            )
                        )
                    },
                    sheetState = bottomSheetState
                ) {
                    Column(
                        modifier = Modifier.wrapContentHeight()
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "新增分类",
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                    }.invokeOnCompletion {
                                        if (state.isShowBottomSheet) {
                                            viewModel.sendEvent(
                                                TaskCategoryScreenViewModel.TaskCategoryEvent.ShowBottomSheet(
                                                    false
                                                )
                                            )
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "删除"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        var categoryName by remember { mutableStateOf("") }

                        var selectCategoryColor by remember {
                            mutableStateOf(
                                defaultSupportCategoryColors.first()
                            )
                        }

                        OutlinedTextField(
                            value = categoryName,
                            label = {
                                Text("分类名称")
                            },
                            leadingIcon = {
                                Box(
                                    modifier = Modifier.size(24.dp)
                                        .clip(CircleShape).background(
                                            color = selectCategoryColor
                                        )
                                )
                            },
                            onValueChange = {
                                categoryName = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = MaterialTheme.shapes.large,
                            colors = OutlinedTextFieldDefaults.colors(
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            "选择颜色",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Start)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            defaultSupportCategoryColors.forEach { color ->
                                Box(
                                    modifier = Modifier.size(32.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .clickable {
                                            selectCategoryColor = color
                                        }
                                )

                            }
                        }


                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            modifier = Modifier.padding(bottom = 10.dp)
                                .fillMaxWidth()
                                .height(56.dp),
                            onClick = {
                                if (categoryName.isNotBlank()) {
                                    val taskCategory = TaskCategory(
                                        name = categoryName,
                                        color = selectCategoryColor.toArgb()
                                    )
                                    val hideJob = coroutineScope.launch(start = CoroutineStart.LAZY) {
                                            bottomSheetState.hide()
                                        }
                                    viewModel.sendEvent(
                                        TaskCategoryScreenViewModel.TaskCategoryEvent.AddTaskCategory(
                                            taskCategory, hideJob
                                        )
                                    )

                                } else {
                                    viewModel.sendEvent(
                                        TaskCategoryScreenViewModel.TaskCategoryEvent.ShowSnackBar(
                                            "类型名称不能为空"
                                        )
                                    )
                                }

                            },
                            shape = MaterialTheme.shapes.large
                        ) {
                            Text(
                                "保存", style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.align(Alignment.CenterVertically),
                                textAlign = TextAlign.Center
                            )
                        }


                    }
                }
            }

        }
    }
}