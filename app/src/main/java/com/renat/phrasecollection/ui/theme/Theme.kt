package com.renat.phrasecollection.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FuwaFuwaPink = lightColorScheme(
    // 上部アプリバーや主要アクションの基準色
    primary = Color(0xFFFFA8D8),
    onPrimary = Color(0xFFFFFFFF),
    // 画面下部のナビゲーションバー背景色
    primaryContainer = Color(0xFFFFD8EC),
    onPrimaryContainer = Color(0xFF7A2F59),
    // 登録ボタンや選択状態などのアクセント色
    secondary = Color(0xFF9EDCFF),
    onSecondary = Color(0xFF1D4560),
    // チップや補助的な面の背景色
    secondaryContainer = Color(0xFFDDF3FF),
    onSecondaryContainer = Color(0xFF24465C),
    // 小さな強調や楽しい印象を出す色
    tertiary = Color(0xFFFFE58A),
    onTertiary = Color(0xFF5A4400),
    // 控えめな強調背景
    tertiaryContainer = Color(0xFFFFF4C9),
    onTertiaryContainer = Color(0xFF5A4400),
    // 画面全体の背景
    background = Color(0xFFFFF8FC),
    onBackground = Color(0xFF5A4A53),
    // カードや入力欄などの面
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF5A4A53),
    // カテゴリチップや区切り面
    surfaceVariant = Color(0xFFF8E8F2),
    onSurfaceVariant = Color(0xFF75606B),
    // 入力欄やカード境界線
    outline = Color(0xFFE6BDD2),
    // 薄い境界線。強く見せすぎない区切り色。
    outlineVariant = Color(0xFFF4DDEA),
    // エラー表示
    error = Color(0xFFFF6B8F),
    onError = Color.White
)

private val Chamomile = lightColorScheme(
    primary = Color(0xFFD89323),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFF6DFB3),
    onPrimaryContainer = Color(0xFF4A3100),
    secondary = Color(0xFFA67C3D),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFEEE0C8),
    onSecondaryContainer = Color(0xFF3D2D14),
    tertiary = Color(0xFF7A8C4E),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFE2EAC8),
    onTertiaryContainer = Color(0xFF2C3618),
    background = Color(0xFFFFF9F4),
    onBackground = Color(0xFF33362F),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF33362F),
    surfaceVariant = Color(0xFFF0E6D8),
    onSurfaceVariant = Color(0xFF5C543F),
    outline = Color(0xFFC9B68E),
    outlineVariant = Color(0xFFE6D9BE),
    error = Color(0xFFD43B3B),
    onError = Color.White
)

private val Lavender = lightColorScheme(
    primary = Color(0xFFA883A9),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFF3DDF0),
    onPrimaryContainer = Color(0xFF40233E),
    secondary = Color(0xFFC98775),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFF6DAD2),
    onSecondaryContainer = Color(0xFF4A2A1E),
    tertiary = Color(0xFF8A604D),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFEEDACE),
    onTertiaryContainer = Color(0xFF3A2417),
    background = Color(0xFFFDECED),
    onBackground = Color(0xFF8A604D),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF8A604D),
    surfaceVariant = Color(0xFFF1DCDA),
    onSurfaceVariant = Color(0xFF8B6F6A),
    outline = Color(0xFFD7B9BC),
    outlineVariant = Color(0xFFF0DFDD),
    error = Color(0xFFC2453D),
    onError = Color.White
)

private val PailPinkgray = lightColorScheme(
    primary = Color(0xFFCCB3B7),
    onPrimary = Color(0xFF3A2A2C),
    primaryContainer = Color(0xFFEFE2E4),
    onPrimaryContainer = Color(0xFF3A2A2C),
    secondary = Color(0xFF5C6B8A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD7DCEC),
    onSecondaryContainer = Color(0xFF1F2A3D),
    tertiary = Color(0xFF7E8C6F),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDDE4D2),
    onTertiaryContainer = Color(0xFF2C351F),
    background = Color(0xFFE6E8F1),
    onBackground = Color(0xFF22292C),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF22292C),
    surfaceVariant = Color(0xFFDCDFE8),
    onSurfaceVariant = Color(0xFF565E66),
    outline = Color(0xFFAEB3C2),
    outlineVariant = Color(0xFFD5D8E4),
    error = Color(0xFFB3261E),
    onError = Color.White
)

private val PailBluegray = lightColorScheme(
    primary = Color(0xFFB3C2CC),
    onPrimary = Color(0xFF2A323A),
    primaryContainer = Color(0xFFE2E9EF),
    onPrimaryContainer = Color(0xFF2A323A),
    secondary = Color(0xFF5C6B8A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD7DCEC),
    onSecondaryContainer = Color(0xFF1F2A3D),
    tertiary = Color(0xFF7E8C6F),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDDE4D2),
    onTertiaryContainer = Color(0xFF2C351F),
    background = Color(0xFFE6E8F1),
    onBackground = Color(0xFF22292C),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF22292C),
    surfaceVariant = Color(0xFFDCDFE8),
    onSurfaceVariant = Color(0xFF565E66),
    outline = Color(0xFFAEB3C2),
    outlineVariant = Color(0xFFD5D8E4),
    error = Color(0xFFB3261E),
    onError = Color.White
)

private val LightColors = lightColorScheme(
    primary = Color(0xFFB3C2CC),
    onPrimary = Color(0xFF2A323A),
    primaryContainer = Color(0xFFE2E9EF),
    onPrimaryContainer = Color(0xFF2A323A),
    secondary = Color(0xFF5C6B8A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD7DCEC),
    onSecondaryContainer = Color(0xFF1F2A3D),
    tertiary = Color(0xFF7E8C6F),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDDE4D2),
    onTertiaryContainer = Color(0xFF2C351F),
    background = Color(0xFFE6E8F1),
    onBackground = Color(0xFF22292C),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF22292C),
    surfaceVariant = Color(0xFFDCDFE8),
    onSurfaceVariant = Color(0xFF565E66),
    outline = Color(0xFFAEB3C2),
    outlineVariant = Color(0xFFD5D8E4),
    error = Color(0xFFB3261E),
    onError = Color.White
)

@Composable
fun PhraseCollectionTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = Chamomile,
        typography = MaterialTheme.typography,
        content = content
    )
}
