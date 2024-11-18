package com.hys.hy.designsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
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
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.Start)
            ) { }
            FilterChip(
                modifier = Modifier,
                selected = true,
                onClick = {

                },
                colors = FilterChipDefaults.filterChipColors(),
                elevation = null,
                shape = MaterialTheme.shapes.large,
                label = {
                    val text = "待完成"

                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                },
                leadingIcon = {
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInHorizontally() + fadeIn(),
                        exit = shrinkHorizontally()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            )




            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
//                Text(
//                    text = "3232",
//                    style = MaterialTheme.typography.titleMedium,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                )


                FilterChip(
                    modifier = Modifier.align(Alignment.Top),
                    selected = true,
                    onClick = {

                    },
                    colors = FilterChipDefaults.filterChipColors(),
                    elevation = null,
                    shape = MaterialTheme.shapes.large,
                    label = {
                        val text = "待完成"

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.surface
                        )
                    },
                    leadingIcon = {
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInHorizontally() + fadeIn(),
                            exit = shrinkHorizontally()
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                )
            }
        }


    }
}

//@Preview(showBackground = true)
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Test() {
    HYTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {


                Button(
                    modifier = Modifier.height(50.dp),
                    onClick = {}
                ) { }


                FilterChip(
                    modifier = Modifier.height(40.dp),
                    selected = true,
                    onClick = {

                    },
                    colors = FilterChipDefaults.filterChipColors(),
                    elevation = null,
                    shape = MaterialTheme.shapes.large,
                    label = {
                        val text = "待完成"

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.surface
                        )
                    },
                    leadingIcon = {
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInHorizontally() + fadeIn(),
                            exit = shrinkHorizontally()
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                )

                Text(
                    text = "3232",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

            }
        }
    }
}