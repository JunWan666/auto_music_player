package com.automusic.player.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// UI2: neutral work surface with teal actions and warm rhythm accents.
val Bg = Color(0xFF090C0E)
val Surface1 = Color(0xFF111619)
val Surface2 = Color(0xFF182024)
val Surface3 = Color(0xFF222D32)
val Ink = Color(0xFFF2F6F5)
val Ink2 = Color(0xFFA5B2B0)
val Ink3 = Color(0xFF687774)
val Line = Color(0xFF273236)
val Line2 = Color(0xFF354247)
val Brand = Color(0xFF55D6C2)
val Brand2 = Color(0xFFF2C66D)
val Brand3 = Color(0xFF238D80)
val BrandSoft = Color(0x2455D6C2)
val StateSuccess = Color(0xFF69D391)
val StateWarning = Color(0xFFF2C66D)
val StateError = Color(0xFFFF7C7C)
val StateInfo = Color(0xFF6BB8F0)

private val AmpColorScheme = darkColorScheme(
    primary = Brand,
    onPrimary = Bg,
    primaryContainer = Brand3,
    onPrimaryContainer = Bg,
    secondary = Brand2,
    onSecondary = Bg,
    background = Bg,
    onBackground = Ink,
    surface = Surface1,
    onSurface = Ink,
    surfaceVariant = Surface2,
    onSurfaceVariant = Ink2,
    outline = Line2,
    error = StateError,
    onError = Bg,
)

private val AmpTypography = Typography(
    titleLarge = Typography().titleLarge.copy(
        fontSize = 23.sp,
        lineHeight = 29.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp,
    ),
    titleMedium = Typography().titleMedium.copy(
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp,
    ),
    bodyMedium = Typography().bodyMedium.copy(letterSpacing = 0.sp),
    bodySmall = Typography().bodySmall.copy(lineHeight = 18.sp, letterSpacing = 0.sp),
    labelLarge = Typography().labelLarge.copy(letterSpacing = 0.sp),
    labelMedium = Typography().labelMedium.copy(letterSpacing = 0.sp),
    labelSmall = Typography().labelSmall.copy(letterSpacing = 0.sp),
)

private val AmpShapes = Shapes(
    extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(4.dp),
    small = androidx.compose.foundation.shape.RoundedCornerShape(6.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
)

@Composable
fun AmpTheme(content: @Composable () -> Unit) {
    isSystemInDarkTheme() // App 固定暗色主题,仅保持 API 一致
    MaterialTheme(
        colorScheme = AmpColorScheme,
        typography = AmpTypography,
        shapes = AmpShapes,
        content = content,
    )
}
