package com.hys.hy.designsystem.component.timeline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
data class CircleParameters(
    val radius: Dp,
    val backgroundColor: Color
)

@Stable
data class LineParameters(
    val strokeWidth: Dp,
    val brush: Brush
)

@Stable
enum class TimeLineNodePositionType {
    START,
    MIDDLE,
    END
}


object TimeLineNodeDefaults {

    private val CIRCLE_RADIUS = 12.dp

    private val defaultStrokeWidth = 3.dp

    @Composable
    fun linearGradient(
        strokeWidth: Dp = defaultStrokeWidth,
        startColor: Color = MaterialTheme.colorScheme.onSurface,
        endColor: Color = MaterialTheme.colorScheme.onSurface,
        startY: Float = 0.0f,
        endY: Float = Float.POSITIVE_INFINITY
    ): LineParameters {
        val brush = Brush.verticalGradient(
            colors = listOf(startColor, endColor),
            startY = startY,
            endY = endY
        )
        return LineParameters(strokeWidth, brush)
    }

    @Composable
    fun circleParameters(
        radius: Dp = CIRCLE_RADIUS,
        backgroundColor: Color = MaterialTheme.colorScheme.primary
    ) = CircleParameters(
        radius = radius,
        backgroundColor = backgroundColor
    )


}

@Composable
fun TimelineNode(
    contentStartOffset: Dp = 16.dp,
    spacerBetweenNodes: Dp = 32.dp,
    timeLineNodePositionType: TimeLineNodePositionType = TimeLineNodePositionType.MIDDLE,
    circleParameters: CircleParameters = TimeLineNodeDefaults.circleParameters(),
    lineParameters: LineParameters? = TimeLineNodeDefaults.linearGradient(),
    content: @Composable BoxScope.(modifier: Modifier) -> Unit
) {
    Box(
        modifier = Modifier.wrapContentSize()
            .drawBehind {
                val circleRadiusInPx = circleParameters.radius.toPx()
                drawCircle(
                    color = circleParameters.backgroundColor,
                    radius = circleRadiusInPx,
                    center = Offset(circleRadiusInPx, circleRadiusInPx)
                )
                lineParameters?.let {
                    if (timeLineNodePositionType!=TimeLineNodePositionType.END){
                        drawLine(
                            brush = it.brush,
                            start = Offset(circleRadiusInPx, circleRadiusInPx * 2),
                            end = Offset(circleRadiusInPx, size.height),
                            strokeWidth = it.strokeWidth.toPx()
                        )
                    }
                }

            }
    ) {
        content(
            Modifier
                .padding(start = contentStartOffset + circleParameters.radius * 2)
                .padding(bottom = spacerBetweenNodes)
        )
    }
}



