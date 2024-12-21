package com.hys.hy.designsystem.component.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Filled.QQ: ImageVector
    get() {
        if (_QQ != null) {
            return _QQ!!
        }
        _QQ = ImageVector.Builder(
            name = "QQ",
            defaultWidth = 15.313.dp,
            defaultHeight = 24.5.dp,
            viewportWidth = 15.313f,
            viewportHeight = 24.5f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 0f
            ) {
                moveTo(0f, 0f)
                horizontalLineToRelative(15.313f)
                verticalLineToRelative(24.5f)
                horizontalLineToRelative(-15.313f)
                close()
            }
            group(
                clipPathData = PathData {
                    moveTo(0.273f, 3.5f)
                    horizontalLineToRelative(14.766f)
                    verticalLineToRelative(17.5f)
                    horizontalLineToRelative(-14.766f)
                    close()
                }
            ) {
                path(fill = SolidColor(Color(0xFF4B5563))) {
                    moveTo(14.834f, 18.266f)
                    quadTo(14.595f, 18.266f, 14.253f, 17.821f)
                    quadTo(13.911f, 17.411f, 13.604f, 16.967f)
                    quadTo(13.296f, 16.522f, 13.296f, 16.488f)
                    quadTo(13.296f, 17.309f, 12.852f, 18.231f)
                    quadTo(12.441f, 19.188f, 11.553f, 19.94f)
                    quadTo(12.065f, 20.111f, 12.647f, 20.419f)
                    quadTo(13.262f, 20.727f, 13.125f, 21.137f)
                    quadTo(12.92f, 21.342f, 11.86f, 21.376f)
                    quadTo(10.767f, 21.444f, 9.536f, 21.376f)
                    quadTo(8.306f, 21.342f, 7.656f, 21.273f)
                    quadTo(7.007f, 21.342f, 5.776f, 21.376f)
                    quadTo(4.546f, 21.444f, 3.452f, 21.376f)
                    quadTo(2.393f, 21.342f, 2.187f, 21.137f)
                    quadTo(2.051f, 20.727f, 2.666f, 20.419f)
                    quadTo(3.247f, 20.111f, 3.76f, 19.94f)
                    quadTo(2.871f, 19.188f, 2.461f, 18.231f)
                    quadTo(2.017f, 17.309f, 2.017f, 16.488f)
                    quadTo(2.017f, 16.522f, 1.709f, 16.967f)
                    quadTo(1.401f, 17.411f, 1.06f, 17.821f)
                    quadTo(0.718f, 18.266f, 0.479f, 18.266f)
                    quadTo(0.308f, 18.266f, 0.308f, 17.479f)
                    quadTo(0.273f, 16.659f, 0.82f, 14.882f)
                    quadTo(1.025f, 14.198f, 1.265f, 13.515f)
                    quadTo(1.538f, 12.831f, 1.88f, 12.011f)
                    quadTo(2.017f, 11.669f, 2.187f, 11.259f)
                    quadTo(2.085f, 8.183f, 3.418f, 6.098f)
                    quadTo(4.717f, 3.979f, 7.656f, 3.91f)
                    quadTo(10.562f, 3.979f, 11.895f, 6.063f)
                    quadTo(13.228f, 8.148f, 13.125f, 11.259f)
                    quadTo(13.296f, 11.669f, 13.433f, 12.011f)
                    quadTo(13.774f, 12.831f, 14.048f, 13.515f)
                    quadTo(14.287f, 14.198f, 14.492f, 14.882f)
                    quadTo(15.039f, 16.659f, 15.005f, 17.479f)
                    quadTo(15.005f, 18.266f, 14.834f, 18.266f)
                    close()
                }
            }
        }.build()

        return _QQ!!
    }

@Suppress("ObjectPropertyName")
private var _QQ: ImageVector? = null
