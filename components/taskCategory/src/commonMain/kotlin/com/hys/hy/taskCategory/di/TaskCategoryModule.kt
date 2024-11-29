package com.hys.hy.taskCategory.di


import com.hys.hy.taskCategory.repository.TaskCategoryRepository
import com.hys.hy.taskCategory.repository.TaskCategoryRepositoryImpl
import com.hys.hy.taskCategory.usecase.AddTaskCategoryUseCase
import com.hys.hy.taskCategory.usecase.AddTaskCategoryUseCaseImpl
import com.hys.hy.taskCategory.usecase.DeleteTaskCategoryUseCase
import com.hys.hy.taskCategory.usecase.DeleteCategoryUseCaseImpl
import com.hys.hy.taskCategory.usecase.GetTaskCategoriesUseCase
import com.hys.hy.taskCategory.usecase.GetTaskCategoriesUseCaseImpl
import org.koin.dsl.module

val taskCategoryModule = module {
    single<TaskCategoryRepository> { TaskCategoryRepositoryImpl(get()) }

    factory<GetTaskCategoriesUseCase> { GetTaskCategoriesUseCaseImpl(get()) }

    factory<AddTaskCategoryUseCase> { AddTaskCategoryUseCaseImpl(get()) }

    factory<DeleteTaskCategoryUseCase> { DeleteCategoryUseCaseImpl(get()) }
}

