package com.renat.phrasecollection.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Shows app information and placeholder import/export actions for future versions.
 */
@Composable
fun SettingScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "設定",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text("Phrase Collection App")
        Text("Version 1.0 MVP")
        Text("ローカル保存のみ。サーバー、認証、クラウド同期は使用しません。")
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            onClick = {}
        ) {
            Text("JSONエクスポート（未実装）")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            onClick = {}
        ) {
            Text("JSONインポート（未実装）")
        }
    }
}
