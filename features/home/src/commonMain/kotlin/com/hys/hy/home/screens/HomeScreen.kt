package com.hys.hy.home.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.animation.SinkAnimateScope
import com.hys.hy.designsystem.component.toolbars.HomeTabIndex
import com.hys.hy.designsystem.component.toolbars.NavigationBottomBar
import com.hys.hy.designsystem.theme.CurrentTaskBrush
import com.hys.hy.designsystem.theme.MonthlyItemDoneBrush
import com.hys.hy.designsystem.theme.MonthlyItemImportantBrush
import com.hys.hy.designsystem.theme.MonthlyItemInProgressBrush
import com.hys.hy.designsystem.theme.MonthlyItemNotStartedBrush
import com.hys.hy.home.viewmodel.HomeScreenViewModel
import hy.features.home.generated.resources.Res
import hy.features.home.generated.resources.naixv
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    onSettingTabClick: () -> Unit = {},
    onHomeTabClick: () -> Unit = {},
    onTodayTabClick:()->Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val state by viewModel.container.uiStateFlow.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SinkAnimateScope(
                animatedContentScope
            ) { offset ->
                TopAppBar(
                    modifier = Modifier.offset {
                        offset
                    },
                    title = {
                        Column {
                            Text(
                                text = state.currentDayOfTheWeek,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.padding(2.dp))

                            Text(
                                text = state.currentDayOfTheMonth + " " + state.currentMonth,
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    },
                    actions = {
                        OutlinedCard(
                            onClick = { /* doSomething() */ },
                            modifier = Modifier.size(56.dp),
                            shape = CircleShape,
                            elevation = CardDefaults.outlinedCardElevation(
                                defaultElevation = 2.dp,
                            ),
                            border = CardDefaults.outlinedCardBorder(
                                enabled = true,
                            )
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "avatar",
                                    modifier = Modifier.size(24.dp).clip(CircleShape)
                                        .align(Alignment.Center)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        with(sharedTransitionScope) {
                            Image(
                                painter = painterResource(Res.drawable.naixv),
                                contentDescription = "avatar",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.padding(end = 24.dp).size(56.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }
                )

            }

        },
        bottomBar = {
            with(sharedTransitionScope) {
                NavigationBottomBar(
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState("bottomBar"),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    currentTabIndex = HomeTabIndex,
                    onHomeTabClick = {
                        onHomeTabClick.invoke()
                    },
                    onSettingTabClick = {
                        onSettingTabClick.invoke()
                    },
                    onTodayTabClick = {
                        onTodayTabClick.invoke()
                    }
                )
            }

        }
    ) { innerPadding ->
        SinkAnimateScope(
            animatedContentScope
        ) { offset ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .offset { offset }
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                Greet(
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "本月总览",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))

                MonthlyPreview(
                    modifier = Modifier
                )

            }
        }
    }
}

@Composable
fun MonthlyPreview(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Row {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                MonthlyPreviewItem(
                    modifier = Modifier.height(150.dp).fillMaxWidth(),
                    title = "22",
                    subTitle = "已完成",
                    brush = Brush.MonthlyItemDoneBrush
                )

                Spacer(modifier = Modifier.height(13.dp))

                MonthlyPreviewItem(
                    modifier = Modifier.height(105.dp).fillMaxWidth(),
                    title = "12",
                    subTitle = "重要",
                    brush = Brush.MonthlyItemImportantBrush
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                MonthlyPreviewItem(
                    modifier = Modifier.height(105.dp).fillMaxWidth(),
                    title = "7",
                    subTitle = "进行中",
                    brush = Brush.MonthlyItemInProgressBrush
                )

                Spacer(modifier = Modifier.height(13.dp))

                MonthlyPreviewItem(
                    modifier = Modifier.height(150.dp).fillMaxWidth(),
                    title = "14",
                    subTitle = "未确定代办",
                    brush = Brush.MonthlyItemNotStartedBrush
                )

            }
        }

    }
}


//@Composable
//@Preview
//fun MonthItemPreview() {
//    MonthlyPreviewItem(
//        modifier = Modifier.height(150.dp).fillMaxWidth(),
//        title = "14",
//        subTitle = "5 件任务",
//        color = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.primary,
//            contentColor = MaterialTheme.colorScheme.onPrimary
//        )
//    )
//}


/**
 * A composable function that displays a preview item for the monthly overview.
 *
 * @param modifier The modifier to be applied to the Card.
 * @param title The title text to be displayed, typically representing a number.
 * @param subTitle The subtitle text to be displayed, providing additional context.
 * @param contentColor The color to be used for the content text.
 * @param brush The brush to be used for the background.
 */
@Composable
fun MonthlyPreviewItem(
    modifier: Modifier,
    title: String,
    subTitle: String,
    contentColor: Color = MaterialTheme.colorScheme.surface,
    brush: Brush
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = contentColor
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(
                brush = brush
            ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = subTitle,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )

        }
    }
}


@Composable
fun Greet(
    modifier: Modifier.Companion
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Hi 友利奈绪",
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(4.dp))
        // 今日正在进行的任务
        Text(
            text = "5 件任务在进行中",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier.height(93.dp).fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize().background(
                    brush = Brush.CurrentTaskBrush
                ).padding(10.dp)
            ) {
                Spacer(modifier.weight(1f))
                Row(
                ) {
                    Spacer(modifier.weight(1f))
                    Text(
                        "现在",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }

}
