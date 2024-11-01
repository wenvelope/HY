package com.hys.hy.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.rememberNavController
import com.hys.hy.theme.HYTheme
import hy.composeapp.generated.resources.Res

@Preview
@Composable
fun HomeScreenPreview(){
    Surface {
        HYTheme{
            HomeScreen()
        }
    }
}


sealed class HomeScreen(
    val route:String,
    val name :String,
    val icon:ImageVector
){
    data object Home:HomeScreen("home","home", icon = Icons.Filled.Home)
    data object Search:HomeScreen("search","search", icon = Icons.Filled.Search)
    data object Account:HomeScreen("account","account", icon = Icons.Filled.AccountCircle)
}

val homeScreens = listOf(
    HomeScreen.Home,
    HomeScreen.Search,
    HomeScreen.Account
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){

    val naviController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("返回")
                },
                navigationIcon = {
                    IconButton(onClick = {}){
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                homeScreens.forEach { screen->
                    NavigationBarItem(
                        icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                        label = { Text(screen.name) },
                        selected = naviController.currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            naviController.navigate(screen.route)
                        }
                    )
                }
            }
        }
    ) { innerPadding->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {



        }

    }
}


