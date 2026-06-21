package com.renat.phrasecollection.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFFFF7A2F),
    onPrimary = Color.White,
    secondary = Color(0xFF2EC4B6),
    tertiary = Color(0xFF4D96FF),
    background = Color(0xFFFFF7F0),
    surface = Color(0xFFFFFBF7),
    surfaceVariant = Color(0xFFFFE2CC),
    primaryContainer = Color(0xFFFFE0CC),
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
    // darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        // colorScheme = if (darkTheme) DarkColors else LightColors,    ,白い方が可愛いので
        colorScheme = LightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
