package com.hys.hy.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class HyWidgetReceiver() :GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = HyAppWidget()
}