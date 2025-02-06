package com.hys.hy.navigation

import androidx.lifecycle.ViewModel
import com.hys.hy.preference.AppPreference
import kotlinx.coroutines.runBlocking

class AppViewModel(
    private val appPreference: AppPreference
) : ViewModel() {

    val isLoginIn: Boolean = runBlocking {
        appPreference.isLoginIn()
    }




}