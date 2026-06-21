package com.renat.phrasecollection.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF2F5D50),
    onPrimary = Color.White,
    secondary = Color(0xFF6750A4),
    tertiary = Color(0xFF8A4F2A),
    background = Color(0xFFF6F8FB),
    surface = Color.White,
    surfaceVariant = Color(0xFFE5ECE8)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF9DD0BE),
    secondary = Color(0xFFD0BCFF),
    tertiary = Color(0xFFFFB68A)
)

/**
 * Material 3 theme for the Phrase Collection App.
 */
@Composable
fun PhraseCollectionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
