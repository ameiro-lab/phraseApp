package com.renat.phrasecollection.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.renat.phrasecollection.data.entity.PhraseEntity
import com.renat.phrasecollection.ui.component.CategoryChips
import com.renat.phrasecollection.ui.component.PhraseListItem
import com.renat.phrasecollection.viewmodel.PhraseUiState

/**
 * フレーズ一覧画面
 *
 * 機能
 * ・フレーズ一覧表示
 * ・検索
 * ・カテゴリフィルタ
 * ・編集
 * ・削除
 */
@Composable
fun ListScreen(
    paddingValues: PaddingValues,
    uiState: PhraseUiState,
    onSearchChange: (String) -> Unit,
    onToggleCategory: (Int) -> Unit,
    onClearCategories: () -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (PhraseEntity) -> Unit,
    onMessageShown: () -> Unit
) {

    // Snackbar表示用
    val snackbarHostState = remember { SnackbarHostState() }

    // ViewModelから通知メッセージを受け取ったら表示
    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            onMessageShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->

        // 画面全体をスクロール可能な一覧として構成
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // =========================
            // 検索・フィルタエリア
            // =========================
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    // 画面タイトル
                    Text(
                        text = "単語帳アプリ",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    // フレーズ・メモ検索
                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = onSearchChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("検索") },
                        placeholder = { Text("フレーズまたはメモで検索") }
                    )

                    // カテゴリ見出しと解除ボタン
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "カテゴリ",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.titleSmall
                        )

                        // カテゴリ選択中のみ表示
                        if (uiState.selectedCategoryIds.isNotEmpty()) {
                            TextButton(onClick = onClearCategories) {
                                Text("解除")
                            }
                        }
                    }

                    // カテゴリフィルタチップ
                    CategoryChips(
                        categories = uiState.categories,
                        selectedIds = uiState.selectedCategoryIds,
                        onToggle = onToggleCategory
                    )
                }
            }

            // =========================
            // フレーズ一覧
            // =========================

            // データなし
            if (uiState.phrases.isEmpty()) {

                item {
                    Text(
                        text = "保存済みのフレーズはありません。",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            } else {

                // フレーズ一覧表示
                items(
                    uiState.phrases,
                    key = { it.phrase.id }
                ) { phrase ->

                    // リストを表示する
                    PhraseListItem(
                        phrase = phrase,

                        // 編集画面へ遷移
                        onEditClick = onEditClick,

                        // 削除実行
                        onDeleteClick = {
                            onDeleteClick(phrase.phrase)
                        }
                    )
                }
            }
        }
    }
}