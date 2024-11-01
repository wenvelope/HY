package com.hys.hy.login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hy.features.login.generated.resources.Res
import hy.features.login.generated.resources.bg_welcome
import org.jetbrains.compose.resources.painterResource

@Composable
fun WelcomeScreen(
    onStartClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.bg_welcome),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            containerColor = Color.Unspecified
        ) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp)
            ) {
                Text(
                    "欢迎来到HY",
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 70.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.displayMedium,
                )


                Button(
                    onClick = onStartClick,
                    modifier = Modifier.padding(bottom = 70.dp).height(48.dp).fillMaxWidth().align(Alignment.BottomCenter)

                ) {
                    Text("开始")
                }
            }
        }
    }


}