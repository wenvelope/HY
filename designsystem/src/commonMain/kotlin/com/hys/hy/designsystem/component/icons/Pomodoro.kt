package com.hys.hy.designsystem.component.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Filled.Pomodoro: ImageVector
    get() {
        if (_Pomodoro != null) {
            return _Pomodoro!!
        }
        _Pomodoro = ImageVector.Builder(
            name = "Pomodoro",
            defaultWidth = 32.dp,
            defaultHeight = 32.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color(0xFF3A3A3A))) {
                moveTo(963.1f, 345.4f)
                curveToRelative(-34.4f, -59.4f, -83.5f, -112f, -142.5f, -152.9f)
                curveToRelative(3.8f, -11.4f, 2.2f, -23.9f, -4.3f, -34f)
                arcToRelative(39.6f, 39.6f, 0f, isMoreThanHalf = false, isPositiveArc = false, -29.2f, -17.9f)
                lineTo(617.9f, 123.1f)
                lineToRelative(-73.4f, -105.4f)
                curveToRelative(-7.4f, -10.6f, -19.6f, -17f, -32.5f, -17f)
                reflectiveCurveToRelative(-25.1f, 6.3f, -32.5f, 17f)
                lineTo(406.1f, 123.1f)
                lineTo(236.9f, 140.5f)
                arcTo(39.6f, 39.6f, 0f, isMoreThanHalf = false, isPositiveArc = false, 207.7f, 158.4f)
                arcToRelative(39.7f, 39.7f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.3f, 34f)
                curveTo(77.2f, 279.7f, 2.7f, 415.2f, 2.7f, 560.3f)
                curveToRelative(-0f, 62.7f, 13.8f, 124.7f, 40.7f, 181.4f)
                curveToRelative(25.9f, 55.1f, 62.9f, 104.6f, 110f, 147f)
                curveToRelative(46.8f, 42.1f, 101.2f, 75.1f, 161.7f, 98.2f)
                arcToRelative(559.2f, 559.2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 393.8f, 0f)
                curveToRelative(60.5f, -23f, 114.9f, -56.1f, 161.7f, -98.2f)
                curveToRelative(47.1f, -42.4f, 84.1f, -91.8f, 110f, -147f)
                arcTo(423.3f, 423.3f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1021.3f, 560.3f)
                arcToRelative(429.6f, 429.6f, 0f, isMoreThanHalf = false, isPositiveArc = false, -58.2f, -214.9f)
                close()
                moveTo(432.3f, 200f)
                curveToRelative(11.5f, -1.2f, 21.9f, -7.3f, 28.4f, -16.8f)
                lineTo(512f, 109.6f)
                lineToRelative(51.2f, 73.6f)
                curveToRelative(6.6f, 9.5f, 17f, 15.6f, 28.4f, 16.8f)
                lineToRelative(80.6f, 8.3f)
                lineToRelative(-47.3f, 32.7f)
                arcToRelative(39.6f, 39.6f, 0f, isMoreThanHalf = false, isPositiveArc = false, -16f, 41.9f)
                lineToRelative(14.5f, 59.8f)
                lineToRelative(-97.7f, -36.4f)
                arcToRelative(39.6f, 39.6f, 0f, isMoreThanHalf = false, isPositiveArc = false, -27.7f, 0f)
                lineToRelative(-97.7f, 36.4f)
                lineToRelative(14.5f, -59.8f)
                arcToRelative(39.6f, 39.6f, 0f, isMoreThanHalf = false, isPositiveArc = false, -16f, -41.9f)
                lineToRelative(-47.3f, -32.7f)
                lineToRelative(80.6f, -8.3f)
                close()
                moveTo(817.6f, 829.8f)
                arcToRelative(434.6f, 434.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, -136.9f, 83f)
                arcToRelative(480f, 480f, 0f, isMoreThanHalf = false, isPositiveArc = true, -337.5f, 0f)
                arcToRelative(434.6f, 434.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, -136.9f, -83f)
                curveTo(126.1f, 757.5f, 81.9f, 661.8f, 81.9f, 560.3f)
                curveToRelative(0f, -125.5f, 68.9f, -242.8f, 184.6f, -314.6f)
                lineToRelative(65f, 44.9f)
                lineToRelative(-25.6f, 105.8f)
                arcToRelative(39.6f, 39.6f, 0f, isMoreThanHalf = false, isPositiveArc = false, 52.3f, 46.4f)
                lineTo(512f, 385.7f)
                lineToRelative(153.7f, 57.3f)
                curveToRelative(13.7f, 5.1f, 29.1f, 2.2f, 40.1f, -7.5f)
                arcToRelative(39.6f, 39.6f, 0f, isMoreThanHalf = false, isPositiveArc = false, 12.2f, -38.9f)
                lineToRelative(-25.6f, -105.8f)
                lineToRelative(65f, -44.9f)
                curveToRelative(2.9f, 1.8f, 5.8f, 3.6f, 8.6f, 5.5f)
                curveToRelative(53.6f, 35.1f, 98f, 81.3f, 128.4f, 133.8f)
                curveToRelative(31.6f, 54.5f, 47.5f, 113.5f, 47.5f, 175.2f)
                curveToRelative(0f, 101.5f, -44.2f, 197.2f, -124.4f, 269.4f)
                close()
                moveTo(817.6f, 829.8f)
            }
        }.build()

        return _Pomodoro!!
    }

@Suppress("ObjectPropertyName")
private var _Pomodoro: ImageVector? = null
