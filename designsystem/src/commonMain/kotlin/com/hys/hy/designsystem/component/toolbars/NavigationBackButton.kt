package com.hys.hy.designsystem.component.toolbars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun NavigationBackButton(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit
) {
    OutlinedCard(
        onClick = onBackButtonClick,
        modifier = modifier.size(56.dp),
        shape = CircleShape
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "popBack",
                modifier = Modifier.size(24.dp).clip(CircleShape)
                    .align(Alignment.Center)
            )
        }
    }
}
