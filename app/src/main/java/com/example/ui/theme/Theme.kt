package com.example.ui.theme

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
    primary = WaterBluePrimaryDark,
    onPrimary = Color(0xFF00344F),
    primaryContainer = Color(0xFF004D72),
    onPrimaryContainer = Color(0xFFC7E7FF),
    secondary = SolarGoldSecondaryDark,
    onSecondary = Color(0xFF452B00),
    secondaryContainer = Color(0xFF623F00),
    onSecondaryContainer = Color(0xFFFFDDB3),
    tertiary = CleanCyanTertiaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    outline = OutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = WaterBluePrimary,
    onPrimary = Color.White,
    primaryContainer = WaterBlueContainer,
    onPrimaryContainer = OnWaterBlueContainer,
    secondary = SolarGoldSecondary,
    onSecondary = Color.White,
    secondaryContainer = SolarGoldContainer,
    onSecondaryContainer = OnSolarGoldContainer,
    tertiary = CleanCyanTertiary,
    background = BackgroundLight,
    surface = SurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    outline = OutlineLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Allow custom brand identity by default
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
