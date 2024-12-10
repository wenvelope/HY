package com.hys.hy

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.hys.hy.navigation.App


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        // 使得绘制区域延伸到屏幕边缘 包括 statusBar 和 navigationBar
        enableEdgeToEdge()
        // 设置内容
        setContent {
            App()
        }

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    Thread.sleep(1000)
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    return true

                }
            }
        )
    }
}
