package com.hys.hy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hys.hy.theme.HYTheme
import hy.composeapp.generated.resources.Res
import hy.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    HYTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Compose Multiplatform")
                    },
                    navigationIcon = {
                        Image(
                            painter = painterResource(Res.drawable.compose_multiplatform),
                            contentDescription = "Compose Multiplatform",
                            modifier = Modifier.size(56.dp)
                        )
                        val emptyInsets = WindowInsets(0, 0, 0, 0)
                    },
                    windowInsets = TopAppBarDefaults.windowInsets
                )
            }
        ) {
            paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var visible by remember { mutableStateOf(true) }
                Button(onClick = { visible = !visible }) {
                    Text("Toggle")
                }
                AnimatedVisibility(visible) {
                    Text("Hello, Compose Multiplatform!")
                }
            }
        }
    }
}