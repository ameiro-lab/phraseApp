package com.renat.phrasecollection.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.renat.phrasecollection.model.PhraseWithCategories

/**
 * フレーズ一覧の1行分を表示するカード
 *
 * 表示内容
 * ・フレーズ本文
 * ・カテゴリ
 * ・編集ボタン
 * ・削除ボタン
 */
@Composable
fun PhraseListItem(
    phrase: PhraseWithCategories,
    onEditClick: (Long) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    // フレーズ1件分をカード表示
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.surfaceVariant
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),

            // 各要素間の余白
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // フレーズ本文(英語フレーズ用にフォントを指定)
            Text(
                text = phrase.phrase.text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 0.3.sp
                ),
                fontWeight = FontWeight.SemiBold
            )

            // カテゴリ一覧
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // 多対多対応
                // 1フレーズに複数カテゴリを表示
                phrase.categories.forEach { category ->

                    AssistChip(
                        onClick = {},
                        label = { Text(category.name) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }

            // 編集・削除アクション
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // 編集画面へ遷移
                OutlinedButton(
                    onClick = {
                        onEditClick(phrase.phrase.id)
                    }
                ) {
                    Text("編集")
                }

                // フレーズ削除
                TextButton(
                    onClick = onDeleteClick
                ) {
                    Text("削除")
                }
            }
        }
    }
}