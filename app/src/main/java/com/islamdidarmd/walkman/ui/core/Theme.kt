package com.islamdidarmd.walkman.ui.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColors(
    primary = orange,
    primaryVariant = orange,
    secondary = orange,
    background = black,
    surface = dark,
    onPrimary = black,
    onSecondary = white,
    onBackground = white,
    onSurface = onDark,
)

private val LightColorPalette = lightColors(
    primary = orange,
    primaryVariant = orange,
    secondary = orange,
    background = white,
    surface = light,
    onPrimary = white,
    onSecondary = black,
    onBackground = black,
    onSurface = dark,
)

@Composable
fun WalkmanTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = DarkColorPalette,
        typography = typography,
        shapes = shapes,
        content = content
    )
}