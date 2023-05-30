package com.mastour.mastour.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = md_theme_light_primary,
    primaryVariant = md_theme_light_primaryContainer,
    secondary = md_theme_light_secondary,
    secondaryVariant = md_theme_light_secondaryContainer,
)

@Composable
fun MasTourTheme(content: @Composable () -> Unit) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}