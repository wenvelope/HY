package com.hys.hy.task.di

import com.hys.hy.task.repository.TaskRepository
import com.hys.hy.task.repository.TaskRepositoryImpl
import com.hys.hy.task.usecase.AddTaskUseCase
import com.hys.hy.task.usecase.AddTaskUseCaseImpl
import com.hys.hy.task.usecase.ChangeTaskIsDoneUseCase
import com.hys.hy.task.usecase.ChangeTaskIsDoneUseCaseImpl
import com.hys.hy.task.usecase.GetCurrentDayTasksUseCase
import com.hys.hy.task.usecase.GetCurrentDayTasksUseCaseImpl
import com.hys.hy.task.usecase.GetMonthTasksByUserAndDateUseCase
import com.hys.hy.task.usecase.GetMonthTasksByUserAndDateUseCaseImpl
import com.hys.hy.task.usecase.GetMonthsTaskWithCategoryByUser
import com.hys.hy.task.usecase.GetMonthsTaskWithCategoryByUserImpl
import com.hys.hy.task.usecase.GetTaskWithCategoryByParams
import com.hys.hy.task.usecase.GetTaskWithCategoryByParamsImpl
import com.hys.hy.task.usecase.GetTasksByUserAndDateUseCase
import com.hys.hy.task.usecase.GetTasksByUserAndDateUseCaseImpl
import org.koin.dsl.module

val taskModule = module {
    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }

    factory<GetCurrentDayTasksUseCase> {
        GetCurrentDayTasksUseCaseImpl(get())
    }

    factory<AddTaskUseCase> {
        AddTaskUseCaseImpl(get())
    }

    factory<GetTasksByUserAndDateUseCase> {
        GetTasksByUserAndDateUseCaseImpl(get())
    }

    factory<ChangeTaskIsDoneUseCase> {
        ChangeTaskIsDoneUseCaseImpl(get())
    }

    factory<GetMonthTasksByUserAndDateUseCase> {
        GetMonthTasksByUserAndDateUseCaseImpl(get())
    }

    factory<GetMonthsTaskWithCategoryByUser> {
        GetMonthsTaskWithCategoryByUserImpl(get())
    }
    factory<GetTaskWithCategoryByParams> {
        GetTaskWithCategoryByParamsImpl(get())
    }

}