package com.example.deadquiz.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = D1,
    onPrimary = D4,
    secondary = D2,
    onSecondary = D4,
    tertiary = D3,
    background = D4,    // Ciemne tło dla Dark Mode
    surface = D4,
    onBackground = D1,
    onSurface = D1
)

private val LightColorScheme = lightColorScheme(
    primary = D4,
    onPrimary = D1,
    secondary = D2,
    onSecondary = D1,
    tertiary = D3,
    background = D1,
    surface = D1,
    onBackground = D4,
    onSurface = D4
)

@Composable
fun DeadQuizTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, 
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
