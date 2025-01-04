package com.hys.hy.pomodoro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hys.hy.task.entities.Task
import com.hys.hy.task.usecase.GetTaskByIdUseCase
import com.hys.hy.viewmodel.Container
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState
import com.hys.hy.viewmodel.containers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PomodoroScreenViewModel(
    private val taskId: String?,
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {

    private lateinit var timerCoroutineScope: CoroutineScope

    enum class PomodoroState {
        Idle,
        Running,
        Rest
    }

    data class PomodoroScreenState(
        val taskId: String?,
        val task: Task? = null,
        val isWhiteNoiseChecked: Boolean = false,
        val pomodoroState: PomodoroState = PomodoroState.Idle,
        val pomodoroTime: Long,
        val restTime: Long,
        val timeText: String
    ) : UiState {

    }

    sealed interface PomodoroScreenEvent : UiEvent {
        data object ChangeTaskInfo : PomodoroScreenEvent
        data class ChangeIsWhiteNoiseChecked(val isWhiteNoiseChecked: Boolean) : PomodoroScreenEvent
        data object StartPomodoro : PomodoroScreenEvent
        data object StopPomodoro : PomodoroScreenEvent
        data object StartPomodoroRest : PomodoroScreenEvent
        data object StopPomodoroRest : PomodoroScreenEvent
    }

    private val _mContainer by containers<PomodoroScreenState, PomodoroScreenEvent>(initialState())
    val container: Container<PomodoroScreenState, PomodoroScreenEvent>
        get() = _mContainer


    fun sendEvent(event: PomodoroScreenEvent) {
        _mContainer.emitEvent(event)
    }


    private fun initialState(): PomodoroScreenState {
        return PomodoroScreenState(
            taskId = taskId,
            pomodoroTime = 25 * 60,
            restTime = 5 * 60,
            timeText = toTimeText(25 * 60)
        )
    }

    init {
        viewModelScope.launch {
            _mContainer.apply {
                uiEventFlow.collect { event ->
                    when (event) {
                        PomodoroScreenEvent.ChangeTaskInfo -> {
                            uiStateFlow.value.taskId?.let {
                                val task = withContext(Dispatchers.IO) {
                                    getTaskByIdUseCase.execute(GetTaskByIdUseCase.Param(it))
                                }
                                updateState {
                                    copy(
                                        task = task
                                    )
                                }
                            }
                        }

                        is PomodoroScreenEvent.ChangeIsWhiteNoiseChecked -> {
                            updateState {
                                copy(
                                    isWhiteNoiseChecked = event.isWhiteNoiseChecked
                                )
                            }
                        }

                        PomodoroScreenEvent.StartPomodoro -> {
                            updateState {
                                copy(
                                    pomodoroState = PomodoroState.Running
                                )
                            }
                            timerCoroutineScope = CoroutineScope(Dispatchers.IO)
                            timerCoroutineScope.launch {
                                var time = uiStateFlow.value.pomodoroTime
                                while (time > 0) {
                                    time -= 1
                                    updateState {
                                        copy(
                                            timeText = toTimeText(time)
                                        )
                                    }
                                    delay(1000)
                                }
                                sendEvent(PomodoroScreenEvent.StartPomodoroRest)
                            }
                        }

                        PomodoroScreenEvent.StartPomodoroRest -> {
                            updateState {
                                copy(
                                    pomodoroState = PomodoroState.Rest
                                )
                            }
                            timerCoroutineScope = CoroutineScope(Dispatchers.IO)
                            timerCoroutineScope.launch {
                                var time = uiStateFlow.value.pomodoroTime
                                while (time > 0) {
                                    time -= 1
                                    updateState {
                                        copy(
                                            timeText = toTimeText(time)
                                        )
                                    }
                                    delay(1000)
                                }
                                sendEvent(PomodoroScreenEvent.StopPomodoro)
                            }
                        }

                        PomodoroScreenEvent.StopPomodoro -> {
                            timerCoroutineScope.cancel()
                            updateState {
                                copy(
                                    pomodoroState = PomodoroState.Idle,
                                    timeText = toTimeText(uiStateFlow.value.pomodoroTime)
                                )
                            }
                        }

                        PomodoroScreenEvent.StopPomodoroRest -> {
                            timerCoroutineScope.cancel()
                            updateState {
                                copy(
                                    pomodoroState = PomodoroState.Idle,
                                    timeText = toTimeText(uiStateFlow.value.pomodoroTime)
                                )
                            }

                        }
                    }
                }
            }
        }

        sendEvent(PomodoroScreenEvent.ChangeTaskInfo)
    }

    private fun toTimeText(time: Long): String {
        return "${(time / 60)}:${(time % 60).toString().padStart(2, '0')}"
    }
}