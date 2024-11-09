package com.hys.hy.today.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.animation.SinkAnimateScope
import com.hys.hy.designsystem.component.toolbars.NavigationBottomBar
import com.hys.hy.designsystem.component.toolbars.TodayTabIndex

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun TodayScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onHomeTabClick: () -> Unit = {},
    onSettingTabClick: () -> Unit = {},
    onTodayTabClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SinkAnimateScope(
                animatedContentScope
            ) { offset ->
                TopAppBar(
                    modifier = Modifier.offset { offset },
                    title = {

                    },
                    navigationIcon = {
                        OutlinedCard(
                            onClick = { /* doSomething() */ },
                            modifier = Modifier.padding(start = 24.dp).size(56.dp),
                            shape = CircleShape,
                            elevation = CardDefaults.outlinedCardElevation(
                                defaultElevation = 2.dp,
                            )
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "avatar",
                                    modifier = Modifier.size(24.dp).clip(CircleShape)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                )
            }

        },
        bottomBar = {
            with(sharedTransitionScope){
                NavigationBottomBar(
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState("bottomBar"),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    currentTabIndex = TodayTabIndex,
                    onSettingTabClick = {
                        onSettingTabClick.invoke()
                    },
                    onHomeTabClick = {
                        onHomeTabClick.invoke()
                    },
                    onTodayTabClick = {
                        onTodayTabClick.invoke()
                    }
                )
            }

        }
    ) { innerPadding ->
        SinkAnimateScope(animatedContentScope) { offset ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .offset { offset }
            ) {
                Text("Today")
            }
        }
    }
}