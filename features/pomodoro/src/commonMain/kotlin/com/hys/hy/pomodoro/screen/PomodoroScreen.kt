package com.hys.hy.pomodoro.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.icons.Stop
import com.hys.hy.designsystem.component.icons.WhiteNoise
import com.hys.hy.designsystem.component.toolbars.NavigationBackButton
import com.hys.hy.pomodoro.viewmodel.PomodoroScreenViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroScreen(
    onBackButtonClick: () -> Unit,
    taskId: String?,
    viewModel: PomodoroScreenViewModel = koinViewModel { parametersOf(taskId) }
) {

    val state by viewModel.container.uiStateFlow.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "番茄专注")
                },
                navigationIcon = {
                    NavigationBackButton {
                        onBackButtonClick()
                    }
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                    }

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

            Box(
                modifier = Modifier.padding(top = 40.dp).align(
                    CenterHorizontally
                )
            ) {
                val clockColor = MaterialTheme.colorScheme.primaryContainer

                Canvas(
                    modifier = Modifier.size(250.dp)
                        .align(Center)
                ) {
                    drawCircle(
                        color = clockColor,
                        center = center
                    )
                }

                Column(
                    modifier = Modifier.align(Center)
                ) {
                    Text(
                        text = state.timeText,
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.align(CenterHorizontally),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "专注中",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(CenterHorizontally),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                }
            }



            Spacer(modifier = Modifier.height(50.dp))



            Button(
                onClick = {
                    when (state.pomodoroState) {
                        PomodoroScreenViewModel.PomodoroState.Idle -> {
                            viewModel.sendEvent(PomodoroScreenViewModel.PomodoroScreenEvent.StartPomodoro)
                        }

                        PomodoroScreenViewModel.PomodoroState.Running -> {
                            viewModel.sendEvent(PomodoroScreenViewModel.PomodoroScreenEvent.StopPomodoro)
                        }

                        PomodoroScreenViewModel.PomodoroState.Rest -> {
                            viewModel.sendEvent(PomodoroScreenViewModel.PomodoroScreenEvent.StopPomodoroRest)
                        }
                    }
                },
                modifier = Modifier.align(CenterHorizontally)
            ) {
                if (state.pomodoroState == PomodoroScreenViewModel.PomodoroState.Idle) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Start"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Stop,
                        contentDescription = "Start",
                        modifier = Modifier.size(24.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(24.dp))


            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp)
                ) {
                    Text(text = "当前任务")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = state.task?.taskTitle ?: "无",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Filled.WhiteNoise,
                        contentDescription = "Pause"
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text("白噪音")

                    Spacer(modifier = Modifier.weight(1f))

                    Switch(
                        checked = state.isWhiteNoiseChecked,
                        onCheckedChange = {
                            viewModel.sendEvent(
                                PomodoroScreenViewModel.PomodoroScreenEvent.ChangeIsWhiteNoiseChecked(
                                    it
                                )
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


        }


    }
}