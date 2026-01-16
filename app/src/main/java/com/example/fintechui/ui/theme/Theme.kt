package com.example.fintechui.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    // Core surfaces (you already chose these – kept consistent)
    background = Color(0xFF0B0F14),
    surface = Color(0xFF141D2A),
    surfaceVariant = Color(0xFF1A2636),

    // Text / contrast
    onBackground = Color(0xFFE7EDF5),
    onSurface = Color(0xFFE7EDF5),
    onSurfaceVariant = Color(0xFFB6C2D3),

    // Outline / dividers
    outline = Color(0xFF344A63),

    // Primary accent (cyan = “trading UI” vibe)
    primary = Color(0xFF5BE3FF),
    onPrimary = Color(0xFF003540),
    primaryContainer = Color(0xFF0B2E38),
    onPrimaryContainer = Color(0xFFB7F3FF),

    // Secondary (muted steel)
    secondary = Color(0xFF9DB3C7),
    onSecondary = Color(0xFF0D141B),
    secondaryContainer = Color(0xFF1C2A36),
    onSecondaryContainer = Color(0xFFCFE3F6),

    // Tertiary (purple highlight for “premium” moments)
    tertiary = Color(0xFFB7A5FF),
    onTertiary = Color(0xFF1B0B4B),
    tertiaryContainer = Color(0xFF261B55),
    onTertiaryContainer = Color(0xFFE6DEFF),

    // Error (loss)
    error = Color(0xFFFF5A6A),
    onError = Color(0xFF2A0006),
    errorContainer = Color(0xFF3A0B12),
    onErrorContainer = Color(0xFFFFD9DD)
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun FintechUITheme(
    // dark-mode-first: default true, and we won't read system setting
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            // Use dynamic DARK colors if available
            dynamicDarkColorScheme(context)
        }
        else -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}