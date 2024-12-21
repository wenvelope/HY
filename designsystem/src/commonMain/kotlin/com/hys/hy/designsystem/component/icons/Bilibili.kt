package com.hys.hy.designsystem.component.icons



import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Filled.Bilibili: ImageVector
    get() {
        if (_Bilibili != null) {
            return _Bilibili!!
        }
        _Bilibili = ImageVector.Builder(
            name = "Bilibili",
            defaultWidth = 17.5.dp,
            defaultHeight = 24.5.dp,
            viewportWidth = 17.5f,
            viewportHeight = 24.5f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0f
            ) {
                moveTo(0f, 0f)
                horizontalLineToRelative(17.5f)
                verticalLineToRelative(24.5f)
                horizontalLineToRelative(-17.5f)
                close()
            }
            group(
                clipPathData = PathData {
                    moveTo(0f, 3.5f)
                    horizontalLineToRelative(17.5f)
                    verticalLineToRelative(17.5f)
                    horizontalLineToRelative(-17.5f)
                    close()
                }
            ) {
                path(fill = SolidColor(Color(0xFF4B5563))) {
                    moveTo(16.649f, 7.465f)
                    quadTo(17.5f, 8.388f, 17.432f, 9.721f)
                    lineTo(17.432f, 16.625f)
                    quadTo(17.398f, 17.992f, 16.513f, 18.847f)
                    quadTo(15.661f, 19.735f, 14.3f, 19.77f)
                    lineTo(3.132f, 19.77f)
                    quadTo(1.77f, 19.735f, 0.919f, 18.847f)
                    quadTo(0.034f, 17.958f, 0f, 16.488f)
                    lineTo(0f, 9.721f)
                    quadTo(0.034f, 8.388f, 0.919f, 7.465f)
                    quadTo(1.77f, 6.61f, 3.132f, 6.576f)
                    lineTo(4.12f, 6.576f)
                    lineTo(3.268f, 5.688f)
                    quadTo(2.962f, 5.414f, 2.962f, 4.936f)
                    quadTo(2.962f, 4.491f, 3.268f, 4.218f)
                    quadTo(3.575f, 3.91f, 4.018f, 3.91f)
                    quadTo(4.46f, 3.91f, 4.767f, 4.218f)
                    lineTo(7.252f, 6.576f)
                    lineTo(10.248f, 6.576f)
                    lineTo(12.802f, 4.218f)
                    quadTo(13.108f, 3.91f, 13.551f, 3.91f)
                    quadTo(13.993f, 3.91f, 14.3f, 4.218f)
                    quadTo(14.572f, 4.491f, 14.606f, 4.936f)
                    quadTo(14.572f, 5.414f, 14.3f, 5.688f)
                    lineTo(13.448f, 6.576f)
                    lineTo(14.436f, 6.576f)
                    quadTo(15.798f, 6.61f, 16.649f, 7.465f)
                    close()
                    moveTo(15.321f, 9.857f)
                    quadTo(15.287f, 9.345f, 14.946f, 9.037f)
                    quadTo(14.64f, 8.73f, 14.163f, 8.73f)
                    lineTo(3.268f, 8.73f)
                    quadTo(2.792f, 8.73f, 2.451f, 9.037f)
                    quadTo(2.145f, 9.345f, 2.145f, 9.857f)
                    lineTo(2.145f, 16.488f)
                    quadTo(2.145f, 16.967f, 2.451f, 17.309f)
                    quadTo(2.792f, 17.616f, 3.268f, 17.65f)
                    lineTo(14.163f, 17.65f)
                    quadTo(14.64f, 17.616f, 14.981f, 17.309f)
                    quadTo(15.287f, 16.967f, 15.321f, 16.488f)
                    lineTo(15.321f, 9.857f)
                    close()
                    moveTo(6.333f, 11.327f)
                    quadTo(6.639f, 11.635f, 6.673f, 12.113f)
                    lineTo(6.673f, 13.241f)
                    quadTo(6.639f, 13.72f, 6.333f, 14.027f)
                    quadTo(6.026f, 14.369f, 5.516f, 14.369f)
                    quadTo(5.039f, 14.369f, 4.732f, 14.027f)
                    quadTo(4.392f, 13.72f, 4.392f, 13.241f)
                    lineTo(4.392f, 12.113f)
                    quadTo(4.392f, 11.635f, 4.732f, 11.327f)
                    quadTo(5.039f, 10.985f, 5.516f, 10.985f)
                    quadTo(5.992f, 10.985f, 6.333f, 11.327f)
                    close()
                    moveTo(12.836f, 11.327f)
                    quadTo(13.142f, 11.635f, 13.176f, 12.113f)
                    lineTo(13.176f, 13.241f)
                    quadTo(13.142f, 13.72f, 12.836f, 14.027f)
                    quadTo(12.529f, 14.369f, 12.052f, 14.369f)
                    quadTo(11.542f, 14.369f, 11.235f, 14.027f)
                    quadTo(10.895f, 13.72f, 10.895f, 13.241f)
                    lineTo(10.895f, 12.113f)
                    quadTo(10.929f, 11.635f, 11.235f, 11.327f)
                    quadTo(11.576f, 10.985f, 12.052f, 10.985f)
                    quadTo(12.529f, 10.985f, 12.836f, 11.327f)
                    close()
                }
            }
        }.build()

        return _Bilibili!!
    }

@Suppress("ObjectPropertyName")
private var _Bilibili: ImageVector? = null
