package com.example.code_test

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val MainColor = Color(0xFF364361)

val LightColorPalette = lightColorScheme(
    primary = MainColor,
    onPrimary = Color.White,
    secondary = Color(0xFF4E6B8D),
    onSecondary = Color.White,
    background = Color(0xFFF5F5F5),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

val DarkColorPalette = darkColorScheme(
    primary = MainColor,
    onPrimary = Color.White,
    secondary = Color(0xFF4E6B8D),
    onSecondary = Color.White,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1F1F1F),
    onSurface = Color.White,
)


@Composable
fun GitHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors, content = content
    )
}