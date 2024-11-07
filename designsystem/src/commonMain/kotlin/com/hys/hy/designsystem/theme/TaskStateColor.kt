package com.hys.hy.designsystem.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val Color.Companion.MonthlyItemDoneColor: Color
    get() = Color(0xFF00B288)

val Brush.Companion.MonthlyItemDoneBrush: Brush
    get() = Brush.linearGradient(
        colors = listOf(
            Color(0xFFA9FFEA),
            Color(0xFF00B288)
        )
    )


val Color.Companion.MonthlyItemInProgressColor: Color
    get() = Color(0xFFFF9E2D)


val Brush.Companion.MonthlyItemInProgressBrush: Brush
    get() = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFD29D),
            Color(0xFFFF9E2D)
        )
    )

val Color.Companion.MonthlyItemNotStartedColor: Color
    get() = Color(0xFF29BAE2)

val Brush.Companion.MonthlyItemNotStartedBrush: Brush
    get() = Brush.linearGradient(
        colors = listOf(
            Color(0xFFB1EEFF),
            Color(0xFF29BAE2)
        )
    )

val Color.Companion.MonthlyItemImportantColor: Color
    get() = Color(0xFFFF1B5E)

val Brush.Companion.MonthlyItemImportantBrush: Brush
    get() = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFA0BC),
            Color(0xFFFF1B5E)
        )
    )

val Brush.Companion.CurrentTaskBrush: Brush
    get() = Brush.linearGradient(
        colors = listOf(
            Color(0xFF8B78FF),
            Color(0xFF5451D6)
        )
    )