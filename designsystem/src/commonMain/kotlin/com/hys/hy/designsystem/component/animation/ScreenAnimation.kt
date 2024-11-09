package com.hys.hy.designsystem.component.animation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateIntOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.IntOffset


/**
 *  对内部内容在页面显示的时候进行一个下沉的动画
 *
 *
 *  @param animatedContentScope 传入页面接受的composable的实例
 *  @param content 应用动画的可组合项
 */
@Composable
fun SinkAnimateScope(
    animatedContentScope: AnimatedContentScope,
    content: @Composable (IntOffset) -> Unit
) {
    AnimatedVisibility(
        visible = animatedContentScope.transition.currentState == EnterExitState.Visible,
        enter = EnterTransition.None,
        exit = ExitTransition.None,
    ) {

        val offset by with(this@AnimatedVisibility) {
            transition.animateIntOffset(
                transitionSpec = {
                    androidx.compose.animation.core.spring(
                        dampingRatio = androidx.compose.animation.core.Spring.DampingRatioNoBouncy,
                        stiffness = androidx.compose.animation.core.Spring.StiffnessMedium
                    )
                }
            ) { state ->
                if (state == EnterExitState.Visible) IntOffset(0, 0) else IntOffset(0, 20)
            }
        }

        content(offset)
    }
}
