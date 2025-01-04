package com.hys.hy.pomodoro.di


import com.hys.hy.pomodoro.viewmodel.PomodoroScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val pomodoroModule = lazyModule {
    viewModel { (taskId: String?) ->
        PomodoroScreenViewModel(
            taskId = taskId,
            getTaskByIdUseCase = get(),
        )
    }
}