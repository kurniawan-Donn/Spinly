package com.example.spinly.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── Semua warna app ──
object SpinColors {
    val BgDark        = Color(0xFF0D1B2A)
    val BgCard        = Color(0xFF1A2940)
    val BgCardLight   = Color(0xFF1E3050)
    val BgInput       = Color(0xFF162235)
    val BgDrawer      = Color(0xFF0F1F30)

    val AccentBlue    = Color(0xFF2E86FF)
    val AccentGreen   = Color(0xFF00C853)
    val AccentRed     = Color(0xFFE63946)
    val AccentOrange  = Color(0xFFF4A261)
    val AccentYellow  = Color(0xFFFFD60A)

    val TextPrimary   = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFF8FA8C8)
    val TextHint      = Color(0xFF4A6580)
    val Divider       = Color(0xFF1E3050)

    val WheelColors = listOf(
        Color(0xFFE63946),
        Color(0xFF457B9D),
        Color(0xFF2A9D8F),
        Color(0xFFE9C46A),
        Color(0xFFF4A261),
        Color(0xFF6A4C93),
    )
}

private val DarkColorScheme = darkColorScheme(
    primary          = SpinColors.AccentBlue,
    onPrimary        = Color.White,
    secondary        = SpinColors.AccentGreen,
    onSecondary      = Color.White,
    error            = SpinColors.AccentRed,
    background       = SpinColors.BgDark,
    onBackground     = SpinColors.TextPrimary,
    surface          = SpinColors.BgCard,
    onSurface        = SpinColors.TextPrimary,
    surfaceVariant   = SpinColors.BgCardLight,
    onSurfaceVariant = SpinColors.TextSecondary,
    outline          = SpinColors.Divider,
)

@Composable
fun SpinAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content     = content,
    )
}