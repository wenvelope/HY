package com.hys.hy.today.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hys.hy.today.viewmodel.TodayScreenViewModel


@Composable
fun MonthSelector(
    onLeftMonthClick: () -> Unit,
    onRightMonthClick: () -> Unit,
    state: TodayScreenViewModel.TodayState
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 18.dp)
            .padding(top = 10.dp)
    ) {
        IconButton(
            modifier = Modifier.height(45.dp).width(60.dp)
                .align(Alignment.CenterStart),
            onClick = { onLeftMonthClick.invoke() }
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp).align(Alignment.CenterVertically)
                )
                Text(
                    state.forwardMonth.name.substring(0, 3).lowercase().replaceFirstChar {
                        it.uppercase()
                    },
                    modifier = Modifier.padding(start = 5.dp),
                    maxLines = 1
                )
            }
        }

        Text(
            text = state.currentSelectMonth.name,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            modifier = Modifier.height(45.dp).width(60.dp)
                .align(Alignment.CenterEnd),
            onClick = { onRightMonthClick.invoke() }
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(state.nextMonth.name.substring(0, 3).lowercase().replaceFirstChar {
                    it.uppercase()
                }, modifier = Modifier.padding(end = 5.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp).align(Alignment.CenterVertically)
                )

            }
        }
    }
}