package com.hys.hy.search.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
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
                .padding(horizontal = 20.dp)
        ) {

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


            LazyColumn(modifier = Modifier.fillMaxSize()) {

            }
        }

    }
}