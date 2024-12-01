package com.hys.hy.search.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.designsystem.component.toolbars.SearchBar
import com.hys.hy.search.viewmodel.SearchScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(
    ExperimentalLayoutApi::class
)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchScreenViewModel = koinViewModel()
) {

    val state by viewModel.container.uiStateFlow.collectAsState()

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
                            "32",
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

            Column (
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
            ){
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(4) {
                        FilterChip(
                            selected = false,
                            onClick = {},
                            label = {
                                Text("全部")
                            }
                        )
                    }
                }

                LazyColumn {

                }
            }



            AnimatedVisibility(
                visible = state.isShowSearchSuggestList,
                enter = slideInVertically { -it/10 },
                exit = fadeOut()
            ){
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
                            repeat(1){
                                ListItem(
                                    modifier = Modifier.clickable {
                                        viewModel.sendEvent(
                                            SearchScreenViewModel.SearchScreenEvent.RequestCloseSearchSuggestList(
                                                true
                                            )
                                        )
                                    },
                                    colors = ListItemDefaults.colors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
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
}