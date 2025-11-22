package com.zkk.wallet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    background = SoftWhite,
    surface = SoftWhite,
    onPrimary = SoftWhite,
    onSecondary = SoftWhite,
    onBackground = DeepBlack,
    onSurface = DeepBlack
)

private val DarkColors = darkColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    background = DeepBlack,
    surface = DeepBlack,
    onPrimary = SoftWhite,
    onSecondary = SoftWhite,
    onBackground = SoftWhite,
    onSurface = SoftWhite
)

@Composable
fun ZKKYCWalletTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,   // THIS FIXES THE ERROR
        content = content
    )
}
