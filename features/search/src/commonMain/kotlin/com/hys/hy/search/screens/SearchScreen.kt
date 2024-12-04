package com.hys.hy.search.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hys.hy.designsystem.component.animation.SwipeToShowActionBox
import com.hys.hy.designsystem.component.animation.animatePlacement
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.designsystem.component.toolbars.SearchBar
import com.hys.hy.search.viewmodel.SearchScreenViewModel
import hy.features.search.generated.resources.Res
import hy.features.search.generated.resources.button_alarm
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(
    ExperimentalLayoutApi::class
)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchScreenViewModel = koinViewModel(),
    onTaskEditClick: (taskId: String) -> Unit
) {

    val state by viewModel.container.uiStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendEvent(
            SearchScreenViewModel.SearchScreenEvent.GetTaskCategories(userId = "test")
        )
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
            ) {
                NavigationBackButton(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    navController.popBackStack()
                }
                SearchBar(
                    modifier = Modifier.fillMaxSize().padding(vertical = 10.dp),
                    onValueChange = {
                        viewModel.sendEvent(
                            SearchScreenViewModel.SearchScreenEvent.SearchQueryChanged(
                                it
                            )
                        )
                    },
                    value = state.searchQuery,
                    trialIcon = {
                        if (state.searchQuery.isEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        } else {
                            IconButton(
                                onClick = {
                                    viewModel.sendEvent(
                                        SearchScreenViewModel.SearchScreenEvent
                                            .SearchQueryChanged("")
                                    )
                                },
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "clear",
                                )
                            }
                        }

                    },
                    hintText = {
                        Text(
                            "搜索清单，任务",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    },
                )
            }
        })
    { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.animatePlacement()) {
                        var expandedState by remember {
                            mutableStateOf(false)
                        }
                        AssistChip(
                            onClick = {
                                expandedState = true
                            },
                            label = {
                                if (state.queryParamDate == SearchScreenViewModel.QueryParamDate.All) {
                                    Text("日期范围")
                                } else {
                                    Text(state.queryParamDate.name)
                                }
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenu(
                            expanded = expandedState,
                            onDismissRequest = {
                                expandedState = false
                            }
                        ) {
                            SearchScreenViewModel.QueryParamDate.getQueryParamDateList()
                                .forEachIndexed { index, queryParamDate ->
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.sendEvent(
                                                SearchScreenViewModel.SearchScreenEvent.ChangeQueryParamDate(
                                                    queryParamDate
                                                )
                                            )
                                            expandedState = false
                                        },
                                        text = { Text(queryParamDate.name) }
                                    )
                                }
                        }
                    }
                    Box(modifier = Modifier.animatePlacement()) {
                        var expandedState by remember {
                            mutableStateOf(false)
                        }
                        AssistChip(
                            onClick = {
                                expandedState = true
                            },
                            label = {
                                if (state.queryParamCategory == null) {
                                    Text("分类")
                                } else {
                                    Text(state.queryParamCategory!!.name)
                                }
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenu(
                            expanded = expandedState,
                            onDismissRequest = {
                                expandedState = false
                            }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.sendEvent(
                                        SearchScreenViewModel.SearchScreenEvent.ChangeQueryParamCategory(
                                            null
                                        )
                                    )
                                    expandedState = false
                                },
                                text = { Text("全部") }
                            )
                            state.taskCategoryList.forEachIndexed { _, taskCategory ->
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.sendEvent(
                                            SearchScreenViewModel.SearchScreenEvent.ChangeQueryParamCategory(
                                                taskCategory
                                            )
                                        )
                                        expandedState = false
                                    },
                                    text = { Text(taskCategory.name) }
                                )
                            }
                        }
                    }
                    Box(modifier = Modifier.animatePlacement()) {
                        var expandedState by remember {
                            mutableStateOf(false)
                        }
                        AssistChip(
                            onClick = {
                                expandedState = true
                            },
                            label = {
                                if (state.queryParamIsDone == SearchScreenViewModel.QueryParamIsDone.All) {
                                    Text("达成状态")
                                } else {
                                    Text(state.queryParamIsDone.name)
                                }
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenu(
                            expanded = expandedState,
                            onDismissRequest = {
                                expandedState = false
                            }
                        ) {
                            SearchScreenViewModel.QueryParamIsDone.getQueryParamIsDoneList()
                                .forEachIndexed { _, queryParamIsDone ->
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.sendEvent(
                                                SearchScreenViewModel.SearchScreenEvent.ChangeQueryParamIsDone(
                                                    queryParamIsDone
                                                )
                                            )
                                            expandedState = false
                                        },
                                        text = { Text(queryParamIsDone.name) }
                                    )
                                }
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    itemsIndexed(
                        items = state.tasksWithCategoryList,
                        key = { _, item -> item.taskId!! }
                    ) { index, item ->

                        SwipeToShowActionBox(
                            modifier = Modifier.fillMaxWidth().animateItem(),
                            endAction = listOf(
                                {
                                    Box(
                                        modifier = Modifier.fillMaxHeight().width(70.dp).background(
                                            color = Color(0xFF3B82F6)
                                        ).clickable {
                                            onTaskEditClick(item.taskId!!)
                                        }
                                    ) {
                                        Text(
                                            "编辑",
                                            color = Color.White,
                                            style = MaterialTheme.typography.bodyLarge,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.align(Alignment.Center)
                                        )

                                    }
                                },
                                {
                                    Box(
                                        modifier = Modifier.fillMaxHeight().width(70.dp).background(
                                            color = Color(0XFFEF4444)
                                        ).clickable {
                                            viewModel.sendEvent(
                                                SearchScreenViewModel.SearchScreenEvent.DeleteTask(
                                                    item.taskId!!
                                                )
                                            )
                                        }
                                    ) {
                                        Text(
                                            "删除",
                                            color = Color.White,
                                            style = MaterialTheme.typography.bodyLarge,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.align(Alignment.Center)
                                        )

                                    }
                                }
                            )
                        ) {
                            ListItem(
                                modifier = Modifier.fillMaxWidth()
                                    .animateItem(),
                                headlineContent = {
                                    Text(
                                        item.taskTitle,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                },
                                leadingContent = {
                                    Checkbox(
                                        checked = item.isDone,
                                        onCheckedChange = {
                                            viewModel.sendEvent(
                                                SearchScreenViewModel.SearchScreenEvent.ChangeTaskIsDone(
                                                    item.taskId!!,
                                                    !item.isDone
                                                )
                                            )
                                        },
                                        colors = CheckboxDefaults.colors(
                                            uncheckedColor = with(item.taskCategoryColor) {
                                                return@with if (this == null) {
                                                    MaterialTheme.colorScheme.primary
                                                } else {
                                                    Color(this)
                                                }
                                            },
                                            checkedColor = with(item.taskCategoryColor) {
                                                return@with if (this == null) {
                                                    MaterialTheme.colorScheme.primary
                                                } else {
                                                    Color(this)
                                                }
                                            }
                                        )
                                    )
                                },
                                supportingContent = {
                                    item.taskSelectDate?.let { date ->
                                        Text(
                                            "${date.year}年${date.monthNumber}月${date.dayOfMonth}日",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                },
                                trailingContent = {
                                    FilledTonalIconButton(
                                        onClick = {

                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(Res.drawable.button_alarm),
                                            contentDescription = "more",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            )
                        }

                    }
                }
            }



            AnimatedVisibility(
                visible = state.isShowSearchSuggestList,
                enter = slideInVertically { -it / 10 },
                exit = fadeOut()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Card(
                            modifier = Modifier.wrapContentSize().padding(top = 8.dp)
                        ) {
                            ListItem(
                                modifier = Modifier.clickable {
                                    viewModel.sendEvent(
                                        SearchScreenViewModel.SearchScreenEvent.RequestCloseSearchSuggestList(
                                            true
                                        )
                                    )
                                    viewModel.sendEvent(
                                        SearchScreenViewModel.SearchScreenEvent.GetTaskWithCategoryByParams(
                                            state.searchQuery,
                                            state.queryParamIsDone
                                        )
                                    )
                                },
                                colors = ListItemDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                                ),
                                headlineContent = {
                                    Text("搜索 “${state.searchQuery}”")
                                },
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "search",
                                    )
                                }
                            )
                            HorizontalDivider()
                        }
                    }


                }


            }


        }

    }
}