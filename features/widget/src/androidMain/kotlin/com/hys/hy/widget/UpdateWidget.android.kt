package com.hys.hy.widget

actual class UpdateWidgetImpl actual constructor() {

    actual companion object : UpdateWidget {
        actual override suspend fun updateWidget() {
            HyAppWidget.updateWidget()
        }
    }

}