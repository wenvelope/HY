package com.hys.hy.taskcreation.di

import com.hys.hy.taskcreation.viewmodel.TaskCreationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val taskCreationModule = lazyModule {
    viewModel { (taskId: String) ->
        TaskCreationViewModel(
            addTaskUseCase = get(),
            getTaskCategoriesUseCase = get(),
            taskId = taskId,
            getTaskByIdUseCase = get()
        )
    }
}