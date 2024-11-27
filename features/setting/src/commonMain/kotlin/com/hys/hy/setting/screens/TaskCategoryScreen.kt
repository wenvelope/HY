package com.hys.hy.setting.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.setting.viewmodel.SettingScreenModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCategoryScreen(
    onBackClick: () -> Unit,
    viewModel: SettingScreenModel = koinViewModel()
) {
    Scaffold(
        topBar = {
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

        }

    }
}