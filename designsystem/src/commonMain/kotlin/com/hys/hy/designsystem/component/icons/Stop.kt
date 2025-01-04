package com.hys.hy.designsystem.component.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Filled.Stop: ImageVector
    get() {
        if (_Stop != null) {
            return _Stop!!
        }
        _Stop = ImageVector.Builder(
            name = "Stop",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color(0xFF3D3B4F))) {
                moveTo(192.3f, 862.4f)
                curveToRelative(-16f, 0f, -29f, -13.1f, -29f, -29f)
                lineToRelative(0f, -639.4f)
                curveToRelative(0f, -16f, 13.1f, -29f, 29f, -29f)
                lineToRelative(639.4f, 0f)
                curveToRelative(16f, 0f, 29f, 13.1f, 29f, 29f)
                lineToRelative(0f, 639.4f)
                curveToRelative(0f, 16f, -13.1f, 29f, -29f, 29f)
                lineTo(192.3f, 862.4f)
                close()
            }
        }.build()

        return _Stop!!
    }

@Suppress("ObjectPropertyName")
private var _Stop: ImageVector? = null
