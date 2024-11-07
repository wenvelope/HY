package com.hys.hy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hys.hy.navigation.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使得绘制区域延伸到屏幕边缘 包括 statusBar 和 navigationBar
        enableEdgeToEdge()
        // 设置内容
        setContent {
            App()
        }
    }
}
