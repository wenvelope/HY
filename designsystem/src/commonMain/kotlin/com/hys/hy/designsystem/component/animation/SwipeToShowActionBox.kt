package com.hys.hy.designsystem.component.animation

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


enum class SwipeToShowActionBoxValue {
    ShowMainContent,
    ShowStartAction,

    //    ShowStartActionOnly,
    ShowEndAction,
//    ShowEndActionOnly
}


private val SwipeToShowActionVelocityThreshold = 120.dp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeToShowActionBox(
    modifier: Modifier = Modifier,
    startAction: List<@Composable BoxScope.() -> Unit> = listOf(),
    startFullAction: (@Composable BoxScope.() -> Unit)? = null,
    endAction: List<@Composable BoxScope.() -> Unit> = listOf(),
    endFullAction: (@Composable BoxScope.() -> Unit)? = null,
    mainContent: @Composable BoxScope.() -> Unit
) {

    val density = LocalDensity.current

    var mainContentWidth by remember { mutableFloatStateOf(0f) }
    var mainContentHeight by remember { mutableFloatStateOf(0f) }

    var startActionWidthListState by remember { mutableStateOf(List(startAction.size) { 0f }) }
    var endActionWidthListState by remember { mutableStateOf(List(endAction.size) { 0f }) }


    val anchoredDraggableState = remember(
        mainContentWidth,
        mainContentHeight,
        startActionWidthListState,
        endActionWidthListState
    ) {
        AnchoredDraggableState(
            initialValue = SwipeToShowActionBoxValue.ShowMainContent, //初始值，默认展开
            snapAnimationSpec = TweenSpec(durationMillis = 300), //正常速度拖动的动画
            decayAnimationSpec = splineBasedDecay(density), //样条衰减
            positionalThreshold = { distance -> distance * 0.5f }, //位置阈值 50% 时进行锚点切换
            velocityThreshold = { with(density) { SwipeToShowActionVelocityThreshold.toPx() } }, //速度阈值
            anchors = DraggableAnchors {
                SwipeToShowActionBoxValue.ShowStartAction at startActionWidthListState.sum() //左侧全展开锚点宽度 + 左侧展开锚点宽度
                SwipeToShowActionBoxValue.ShowMainContent at 0f
                SwipeToShowActionBoxValue.ShowEndAction at -endActionWidthListState.sum()//右侧全展开锚点宽度 + 右侧展开锚点宽度
            }
        )
    }

    Box(
        modifier = modifier
            .anchoredDraggable(
                state = anchoredDraggableState,
                orientation = Orientation.Horizontal
            )
            .clipToBounds()
    ) {

        startAction.forEachIndexed { index, action ->
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .height(with(density) {
                        mainContentHeight.toDp()
                    }).onSizeChanged {
                        startActionWidthListState =
                            startActionWidthListState.toMutableList().apply {
                                this[index] = it.width.toFloat()
                            }
                    }.offset {
                        val actionWidthPx = startActionWidthListState.sum()
                        IntOffset(
                            x = (-startActionWidthListState[index] + anchoredDraggableState.offset / startActionWidthListState.size * (startActionWidthListState.size - index)).roundToInt(),
                            0
                        )
                    }
            )
            {
                action()
            }
        }

        endAction.forEachIndexed { index, action ->
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .height(with(density) {
                        mainContentHeight.toDp()
                    }).onSizeChanged {
                        endActionWidthListState =
                            endActionWidthListState.toMutableList().apply {
                                this[index] = it.width.toFloat()
                            }
                    }.offset {
                        val actionWidthPx = endActionWidthListState.sum()
                        IntOffset(
                            x = (endActionWidthListState[index] + anchoredDraggableState.offset / endActionWidthListState.size * (endActionWidthListState.size - index)).roundToInt(),
                            0
                        )
                    }
            )
            {
                action()
            }
        }


        Box(modifier = Modifier
            .onSizeChanged {
                mainContentWidth = it.width.toFloat()
                mainContentHeight = it.height.toFloat()
            } .offset {
                IntOffset(
                    x = anchoredDraggableState.offset.roundToInt(),
                    y = 0,
                )
            })
        {
            mainContent()
        }
    }
}

