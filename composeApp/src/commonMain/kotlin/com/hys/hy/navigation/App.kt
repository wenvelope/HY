package com.hys.hy.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hys.hy.designsystem.theme.HYTheme
import com.hys.hy.home.navigation.Home
import com.hys.hy.home.navigation.addHomeScreen
import com.hys.hy.home.navigation.navigateFromLoginToHome
import com.hys.hy.home.navigation.popUpToHome
import com.hys.hy.login.navigation.LoginNavGraph
import com.hys.hy.login.navigation.addLoginNavGraph
import com.hys.hy.login.navigation.navigateSignInWithPopUpToSignIn
import com.hys.hy.search.navigation.addSearchScreen
import com.hys.hy.setting.navigation.addSettingGraph
import com.hys.hy.taskcreation.navigation.addTaskCreationScreen
import com.hys.hy.taskcreation.navigation.navigateTaskCreation
import com.hys.hy.taskcreation.navigation.navigateTaskEdit
import com.hys.hy.today.navigation.addTodayScreen
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun App() {
    HYTheme {
        HYNavHost()
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HYNavHost(
    appViewModel: AppViewModel = koinViewModel()
) {
    SharedTransitionLayout {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = if (appViewModel.isFirstUse) LoginNavGraph else Home
        ) {

            addLoginNavGraph(
                sharedTransitionScope = this@SharedTransitionLayout,
                navController = navController,
                navigateToHome = {
                    navController.navigateFromLoginToHome()
                }
            )

            addHomeScreen(
                onSettingTabClick = navController::navigateSettingWithPopUpToHome,
                sharedTransitionScope = this@SharedTransitionLayout,
                onTodayTabClick = navController::navigateTodayWithPopUpToHome,
                onSearchClick = navController::navigateSearch
            )

            addSettingGraph(
                sharedTransitionScope = this@SharedTransitionLayout,
                navController = navController,
                onHomeTabClick = navController::popUpToHome,
                onTodayTabClick = navController::navigateTodayWithPopUpToHome,
                onLogout = {
                    navController.navigateSignInWithPopUpToSignIn()
                }
            )

            addTodayScreen(
                sharedTransitionScope = this@SharedTransitionLayout,
                onHomeTabClick = navController::popUpToHome,
                onSettingTabClick = navController::navigateSettingWithPopUpToHome,
                onCreateTaskButtonClick = navController::navigateTaskCreation
            )

            addTaskCreationScreen(
                sharedTransitionScope = this@SharedTransitionLayout,
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )

            addSearchScreen(
                sharedTransitionScope = this@SharedTransitionLayout,
                onTaskEditClick = navController::navigateTaskEdit,
                navController = navController
            )


        }


    }
}



