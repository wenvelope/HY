package com.hys.hy.designsystem.component.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun DropDownNestedScope(
    modifier: Modifier,
    hideContentHeightPx: Float,
    lazyListState: LazyListState = rememberLazyListState(),
    hideContent: @Composable BoxScope.(hideContentHeightPx: Float, initialOffsetY: Animatable<Float, AnimationVector1D>) -> Unit,
    content: @Composable (LazyListState) -> Unit
) {

    val scope = rememberCoroutineScope()

    val initialOffsetY = remember {
        Animatable(
            initialValue = 0f,
        )
    }

    val isAtTop by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (source == NestedScrollSource.UserInput && available.y > 0) {
                    scope.launch {
                        initialOffsetY.animateTo(
                            targetValue = initialOffsetY.value + available.y
                        )
                    }

                    if (initialOffsetY.value >= hideContentHeightPx) {
                        scope.launch {
                            initialOffsetY.animateTo(
                                targetValue = hideContentHeightPx
                            )
                        }
                        return Offset(x = 0f, y = 0f)
                    }
                    return Offset(x = 0f, y = available.y)
                } else {
                    return Offset(x = 0f, y = 0f)
                }
            }

            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (source == NestedScrollSource.UserInput && available.y < 0) {
                    scope.launch {
                        initialOffsetY.animateTo(
                            targetValue = initialOffsetY.value + available.y
                        )
                    }
                    if (initialOffsetY.value <= 0) {
                        scope.launch {
                            initialOffsetY.animateTo(
                                targetValue = 0f
                            )
                        }
                        return Offset(x = 0f, y = 0f)
                    }
                    return Offset(x = 0f, y = available.y)
                } else {
                    return Offset.Zero
                }
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (initialOffsetY.value == 0f) {
                    //折叠状态下
                    if (available.y > 0) {
                        //向下滑动
                        if (isAtTop) {
                            scope.launch {
                                initialOffsetY.animateTo(
                                    targetValue = hideContentHeightPx
                                )
                            }
                        }
                    }
                } else if (initialOffsetY.value == hideContentHeightPx) {
                    //展开状态下
                    if (available.y < 0) {
                        //向上滑动
                        scope.launch {
                            initialOffsetY.animateTo(
                                targetValue = 0f
                            )
                        }
                    }
                } else {
                    //中间状态
                    if (available.y > 0f) {
                        //向下滑动
                        if (lazyListState.firstVisibleItemIndex == 0) {
                            scope.launch {
                                initialOffsetY.animateTo(
                                    targetValue = hideContentHeightPx
                                )
                            }
                        }
                    } else if (available.y < 0f) {
                        scope.launch {
                            initialOffsetY.animateTo(
                                targetValue = 0f
                            )
                        }
                    } else {
                        if (initialOffsetY.value > hideContentHeightPx / 3) {
                            scope.launch {
                                initialOffsetY.animateTo(
                                    targetValue = hideContentHeightPx
                                )
                            }
                        } else {
                            scope.launch {
                                initialOffsetY.animateTo(
                                    targetValue = 0f
                                )
                            }
                        }
                    }
                }
                return Velocity.Zero
            }


            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                if (initialOffsetY.value == 0f) {
                    //折叠状态下
                    if (available.y > 6000) {
                        //向下滑动
                        if (isAtTop) {
                            scope.launch {
                                initialOffsetY.animateTo(
                                    targetValue = hideContentHeightPx
                                )
                            }
                        }
                    }
                } else if (initialOffsetY.value == hideContentHeightPx) {
                    //展开状态下
                    if (available.y < 0f) {
                        //向上滑动
                        scope.launch {
                            initialOffsetY.animateTo(
                                targetValue = 0f
                            )
                        }
                    }
                }
                return available
            }

        }
    }



    Box(
        modifier = modifier.nestedScroll(
            connection = nestedScrollConnection
        )
    ) {
        hideContent(
            hideContentHeightPx, initialOffsetY
        )

        Surface(
            Modifier.fillMaxSize().offset {
                IntOffset(x = 0, y = initialOffsetY.value.roundToInt())
            }
        ) {
            content(lazyListState)
        }

    }

}