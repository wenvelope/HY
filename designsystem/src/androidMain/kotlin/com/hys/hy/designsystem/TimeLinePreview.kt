package com.hys.hy.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hys.hy.designsystem.component.timeline.TimeLineNodeDefaults
import com.hys.hy.designsystem.component.timeline.TimelineNode
import com.hys.hy.designsystem.theme.HYTheme

@Composable
private fun MessageBubble(modifier: Modifier, containerColor: Color) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun TimelinePreview() {
    HYTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            TimelineNode(
                lineParameters = TimeLineNodeDefaults.linearGradient(
                    startColor = MaterialTheme.colorScheme.primary,
                    endColor = MaterialTheme.colorScheme.primary
                )
            ) { modifier ->
                MessageBubble(modifier = modifier, containerColor = Color.LightGray)
            }

            TimelineNode { modifier ->
                MessageBubble(modifier = modifier, containerColor = Color.Blue)
            }

            TimelineNode { modifier ->
                MessageBubble(modifier = modifier, containerColor = Color.Red)
            }
        }
    }
}