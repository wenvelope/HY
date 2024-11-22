package com.hys.hy.widget

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.hys.hy.designsystem.theme.MonthlyItemDoneBrush
import com.hys.hy.designsystem.theme.MonthlyItemDoneColor
import com.hys.hy.designsystem.theme.MonthlyItemImportantBrush
import com.hys.hy.designsystem.theme.MonthlyItemImportantColor
import com.hys.hy.designsystem.theme.MonthlyItemInProgressBrush
import com.hys.hy.designsystem.theme.MonthlyItemInProgressColor
import com.hys.hy.task.entities.Task
import com.hys.hy.task.entities.TaskImportance

internal val Task.color: Color
    get() = if (isDone) {
        Color.MonthlyItemDoneColor
    } else if (taskImportance == TaskImportance.IMPORTANT) {
        Color.MonthlyItemImportantColor
    } else {
        Color.MonthlyItemInProgressColor
    }


internal val Task.brush: Brush
    get() = if (isDone) {
        Brush.MonthlyItemDoneBrush
    } else if (taskImportance == TaskImportance.IMPORTANT) {
        Brush.MonthlyItemImportantBrush
    } else {
        Brush.MonthlyItemInProgressBrush
    }
