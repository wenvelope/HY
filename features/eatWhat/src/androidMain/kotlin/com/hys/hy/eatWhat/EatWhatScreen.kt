package com.hys.hy.eatWhat

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EatWhatScreen() {
    var times by remember{ mutableStateOf(1) }
    Row {
        Text("我操${times}")
        Button(
            onClick = {
                times =  times + 1
            }
        ) {
            Text("点击我")
        }
    }
}

@Preview
@Composable
fun EatWhatScreenPreview() {
    EatWhatScreen()
}