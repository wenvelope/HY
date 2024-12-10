package com.hys.hy.search.di

import com.hys.hy.search.viewmodel.SearchScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val searchModule = lazyModule {
    viewModel<SearchScreenViewModel> {
        SearchScreenViewModel(
            getTaskWithCategoryByParams = get(),
            changeTaskIsDoneUseCase = get(),
            getTaskCategoriesUseCase = get(),
            deleteTaskUseCase = get(),
            appPreference = get()
        )
    }
}