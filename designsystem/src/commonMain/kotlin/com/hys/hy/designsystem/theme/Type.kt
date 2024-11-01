package com.hys.hy.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import hy.designsystem.generated.resources.Res
import hy.designsystem.generated.resources.nunitosans_bold
import hy.designsystem.generated.resources.nunitosans_light
import hy.designsystem.generated.resources.nunitosans_semibold
import org.jetbrains.compose.resources.Font


@Composable
fun nunitoScansFamily() = FontFamily(
    Font(Res.font.nunitosans_light, FontWeight.Light),
    Font(Res.font.nunitosans_bold, FontWeight.Bold),
    Font(Res.font.nunitosans_semibold, FontWeight.SemiBold)
)

@Composable
fun appTypography() = Typography().run {
    val nunitoScansFamily = nunitoScansFamily()

        copy(
            headlineLarge = TextStyle(
                fontSize = 22.sp,
                fontFamily = nunitoScansFamily,
                fontWeight = FontWeight.Bold
            ),
            headlineMedium = TextStyle(
                fontSize = 18.sp,
                fontFamily = nunitoScansFamily,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.15.sp
            ),
            headlineSmall = TextStyle(
                fontSize = 14.sp,
                fontFamily = nunitoScansFamily,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.15.sp
            ),
            titleLarge = TextStyle(
                fontSize = 16.sp,
                fontFamily = nunitoScansFamily,
                fontWeight = FontWeight.Light
            ),
            bodyLarge = TextStyle(
                fontSize = 14.sp,
                fontFamily = nunitoScansFamily,
                fontWeight = FontWeight.Light
            ),
            bodyMedium = TextStyle(
                fontSize = 12.sp,
                fontFamily = nunitoScansFamily,
                fontWeight = FontWeight.Light
            ),
            labelLarge = TextStyle(
                fontSize = 14.sp,
                fontFamily = nunitoScansFamily,
                fontWeight = FontWeight.SemiBold
            ),
            labelMedium = TextStyle(
                fontSize = 12.sp,
                fontFamily = nunitoScansFamily,
                fontWeight = FontWeight.SemiBold
            )
        )


}


