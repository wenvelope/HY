package com.hys.hy.task.di

import com.hys.hy.task.repository.TaskRepository
import com.hys.hy.task.repository.TaskRepositoryImpl
import com.hys.hy.task.usecase.AddTaskUseCase
import com.hys.hy.task.usecase.AddTaskUseCaseImpl
import com.hys.hy.task.usecase.GetCurrentDayTasksUseCase
import com.hys.hy.task.usecase.GetCurrentDayTasksUseCaseImpl
import org.koin.dsl.module

val taskModule = module {
    single <TaskRepository>{
        TaskRepositoryImpl(get())
    }

    factory<GetCurrentDayTasksUseCase> {
        GetCurrentDayTasksUseCaseImpl(get())
    }

    factory<AddTaskUseCase> {
        AddTaskUseCaseImpl(get())
    }
}