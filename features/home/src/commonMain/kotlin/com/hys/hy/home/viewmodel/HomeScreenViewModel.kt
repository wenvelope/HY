package com.hys.hy.home.viewmodel

import com.hys.hy.home.util.DataTimeUtil
import com.hys.hy.viewmodel.BaseViewModelCore
import com.hys.hy.viewmodel.MutableContainer
import com.hys.hy.viewmodel.UiEvent
import com.hys.hy.viewmodel.UiState

class HomeScreenViewModel :
    BaseViewModelCore<HomeScreenViewModel.HomeScreenState, HomeScreenViewModel.HomeScreenEvent>() {

    data class HomeScreenState(
        val currentMonth: String,
        val currentDayOfTheWeek: String,
        val currentDayOfTheMonth: String,
    ) : UiState {

    }

    sealed interface HomeScreenEvent : UiEvent {
        data object RefreshScreen : HomeScreenEvent
        data object SwitchTheme : HomeScreenEvent
    }

    override fun initialState(): HomeScreenState {

        //当前日期
        return HomeScreenState(
            currentMonth = DataTimeUtil.getCurrentMonth(),
            currentDayOfTheWeek = DataTimeUtil.getCurrentDayOfTheWeek(),
            currentDayOfTheMonth = DataTimeUtil.getCurrentDayOfMoth().toString(),
        )
    }

    override suspend fun reduce(container: MutableContainer<HomeScreenState, HomeScreenEvent>) {
        container.apply {
            uiEventFlow.collect {
                when (it) {
                    HomeScreenEvent.RefreshScreen -> {
                        updateState {
                            copy(
                                currentMonth = DataTimeUtil.getCurrentMonth(),
                                currentDayOfTheWeek = DataTimeUtil.getCurrentDayOfTheWeek(),
                                currentDayOfTheMonth = DataTimeUtil.getCurrentDayOfMoth()
                                    .toString(),
                            )
                        }
                    }

                    HomeScreenEvent.SwitchTheme -> {

                    }
                }
            }
        }
    }

}


