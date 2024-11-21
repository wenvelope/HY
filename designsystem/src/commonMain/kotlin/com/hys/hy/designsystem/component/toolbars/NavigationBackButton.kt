package com.hys.hy.designsystem.component.toolbars

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun NavigationBackButton(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit
) {

    IconButton(
        modifier = modifier.size(40.dp),
        onClick = onBackButtonClick
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "popBack",
            modifier = Modifier.size(24.dp).clip(CircleShape)
        )
    }
}
