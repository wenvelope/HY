package com.hys.hy.designsystem.component.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Filled.Github: ImageVector
    get() {
        if (_Github != null) {
            return _Github!!
        }
        _Github = ImageVector.Builder(
            name = "Github",
            defaultWidth = 16.953.dp,
            defaultHeight = 24.5.dp,
            viewportWidth = 16.953f,
            viewportHeight = 24.5f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0f
            ) {
                moveTo(0f, 0f)
                horizontalLineToRelative(16.953f)
                verticalLineToRelative(24.5f)
                horizontalLineToRelative(-16.953f)
                close()
            }
            group(
                clipPathData = PathData {
                    moveTo(0f, 3.5f)
                    horizontalLineToRelative(16.953f)
                    verticalLineToRelative(17.5f)
                    horizontalLineToRelative(-16.953f)
                    close()
                }
            ) {
                path(fill = SolidColor(Color(0xFF4B5563))) {
                    moveTo(5.674f, 17.479f)
                    quadTo(5.64f, 17.616f, 5.503f, 17.616f)
                    quadTo(5.298f, 17.616f, 5.298f, 17.479f)
                    quadTo(5.332f, 17.377f, 5.469f, 17.377f)
                    quadTo(5.64f, 17.377f, 5.674f, 17.479f)
                    close()
                    moveTo(4.614f, 17.343f)
                    quadTo(4.58f, 17.445f, 4.751f, 17.514f)
                    quadTo(4.922f, 17.548f, 4.956f, 17.445f)
                    quadTo(4.99f, 17.309f, 4.819f, 17.274f)
                    quadTo(4.648f, 17.24f, 4.614f, 17.343f)
                    close()
                    moveTo(6.118f, 17.274f)
                    quadTo(5.947f, 17.309f, 5.947f, 17.445f)
                    quadTo(5.981f, 17.548f, 6.152f, 17.548f)
                    quadTo(6.323f, 17.479f, 6.323f, 17.377f)
                    quadTo(6.289f, 17.274f, 6.118f, 17.274f)
                    close()
                    moveTo(8.374f, 4.184f)
                    quadTo(4.717f, 4.252f, 2.393f, 6.576f)
                    quadTo(0.068f, 8.9f, 0f, 12.523f)
                    quadTo(0.034f, 15.429f, 1.606f, 17.616f)
                    quadTo(3.179f, 19.804f, 5.811f, 20.692f)
                    quadTo(6.426f, 20.727f, 6.392f, 20.282f)
                    quadTo(6.392f, 20.146f, 6.392f, 19.77f)
                    quadTo(6.392f, 18.983f, 6.392f, 18.197f)
                    quadTo(6.323f, 18.197f, 5.742f, 18.266f)
                    quadTo(5.127f, 18.3f, 4.443f, 18.129f)
                    quadTo(3.76f, 17.924f, 3.486f, 17.172f)
                    quadTo(3.486f, 17.104f, 3.213f, 16.625f)
                    quadTo(2.939f, 16.181f, 2.529f, 15.907f)
                    quadTo(2.461f, 15.873f, 2.256f, 15.634f)
                    quadTo(2.017f, 15.429f, 2.598f, 15.395f)
                    quadTo(2.632f, 15.36f, 3.076f, 15.531f)
                    quadTo(3.521f, 15.668f, 3.896f, 16.283f)
                    quadTo(4.512f, 17.206f, 5.229f, 17.206f)
                    quadTo(5.981f, 17.206f, 6.392f, 17.001f)
                    quadTo(6.528f, 16.181f, 6.938f, 15.839f)
                    quadTo(5.435f, 15.771f, 4.307f, 15.087f)
                    quadTo(3.179f, 14.438f, 3.11f, 12.045f)
                    quadTo(3.11f, 11.361f, 3.315f, 10.917f)
                    quadTo(3.486f, 10.473f, 3.896f, 10.063f)
                    quadTo(3.828f, 9.857f, 3.76f, 9.242f)
                    quadTo(3.691f, 8.627f, 3.999f, 7.738f)
                    quadTo(4.614f, 7.636f, 5.435f, 8.114f)
                    quadTo(6.289f, 8.559f, 6.357f, 8.661f)
                    quadTo(7.383f, 8.354f, 8.511f, 8.354f)
                    quadTo(9.604f, 8.354f, 10.664f, 8.661f)
                    quadTo(10.664f, 8.627f, 11.108f, 8.354f)
                    quadTo(11.519f, 8.08f, 12.065f, 7.875f)
                    quadTo(12.612f, 7.636f, 13.023f, 7.738f)
                    quadTo(13.33f, 8.627f, 13.262f, 9.242f)
                    quadTo(13.193f, 9.857f, 13.091f, 10.063f)
                    quadTo(13.501f, 10.473f, 13.74f, 10.917f)
                    quadTo(13.979f, 11.361f, 13.979f, 12.045f)
                    quadTo(13.945f, 13.686f, 13.398f, 14.472f)
                    quadTo(12.817f, 15.224f, 11.929f, 15.497f)
                    quadTo(11.04f, 15.771f, 10.049f, 15.839f)
                    quadTo(10.596f, 16.215f, 10.63f, 17.411f)
                    quadTo(10.63f, 18.676f, 10.63f, 19.667f)
                    quadTo(10.63f, 20.111f, 10.63f, 20.282f)
                    quadTo(10.596f, 20.727f, 11.211f, 20.692f)
                    quadTo(13.809f, 19.804f, 15.381f, 17.616f)
                    quadTo(16.919f, 15.429f, 16.953f, 12.523f)
                    quadTo(16.919f, 10.131f, 15.791f, 8.251f)
                    quadTo(14.629f, 6.371f, 12.715f, 5.277f)
                    quadTo(10.767f, 4.218f, 8.374f, 4.184f)
                    close()
                    moveTo(3.315f, 15.976f)
                    quadTo(3.247f, 16.044f, 3.35f, 16.146f)
                    quadTo(3.452f, 16.249f, 3.521f, 16.181f)
                    quadTo(3.589f, 16.112f, 3.486f, 16.01f)
                    quadTo(3.418f, 15.907f, 3.315f, 15.976f)
                    close()
                    moveTo(2.939f, 15.702f)
                    quadTo(2.939f, 15.771f, 3.042f, 15.839f)
                    quadTo(3.145f, 15.873f, 3.179f, 15.805f)
                    quadTo(3.213f, 15.736f, 3.11f, 15.668f)
                    quadTo(3.008f, 15.634f, 2.939f, 15.702f)
                    close()
                    moveTo(4.067f, 16.898f)
                    quadTo(3.999f, 17.001f, 4.102f, 17.138f)
                    quadTo(4.238f, 17.24f, 4.341f, 17.172f)
                    quadTo(4.375f, 17.069f, 4.272f, 16.933f)
                    quadTo(4.136f, 16.83f, 4.067f, 16.898f)
                    close()
                    moveTo(3.657f, 16.42f)
                    quadTo(3.589f, 16.488f, 3.657f, 16.625f)
                    quadTo(3.76f, 16.728f, 3.862f, 16.693f)
                    quadTo(3.931f, 16.625f, 3.862f, 16.488f)
                    quadTo(3.76f, 16.352f, 3.657f, 16.42f)
                    close()
                }
            }
        }.build()

        return _Github!!
    }

@Suppress("ObjectPropertyName")
private var _Github: ImageVector? = null
