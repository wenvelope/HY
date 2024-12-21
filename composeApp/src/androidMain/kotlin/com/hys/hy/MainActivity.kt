package com.hys.hy

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import com.hys.hy.navigation.App
import io.github.vinceglb.filekit.core.FileKit


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化 FileKit 文件 图片选择器
        FileKit.init(this)

        installSplashScreen()
        // 使得绘制区域延伸到屏幕边缘 包括 statusBar 和 navigationBar
        enableEdgeToEdge()
        // 设置内容
        setContent {
            ApplySystemBars()
            App()
        }

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    Thread.sleep(200)
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    return true

                }
            }
        )
    }

    // 根据当前模式设置状态栏字体颜色
    @Composable
    fun ApplySystemBars(isDarkMode: Boolean = isSystemInDarkTheme()) {
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)

        LaunchedEffect(isDarkMode) {
            if (isDarkMode) {
                // 如果是深色模式，设置状态栏字体为浅色
                windowInsetsController.isAppearanceLightStatusBars = false
            } else {
                // 如果是浅色模式，设置状态栏字体为深色
                windowInsetsController.isAppearanceLightStatusBars = true
            }
        }
    }
}
