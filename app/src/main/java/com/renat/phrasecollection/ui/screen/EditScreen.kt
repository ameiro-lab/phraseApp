package com.renat.phrasecollection.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.renat.phrasecollection.data.entity.PhraseEntity
import com.renat.phrasecollection.ui.component.CategoryChips
import com.renat.phrasecollection.viewmodel.PhraseUiState

/**
 * Creates or edits a phrase with memo and one or more categories.
 */
@Composable
fun EditScreen(
    paddingValues: PaddingValues,
    uiState: PhraseUiState,
    phraseId: Long,
    onSaveNew: (String, String, Set<Int>) -> Unit,
    onUpdate: ((PhraseEntity, String, String, Set<Int>) -> Unit)? = null,
    onDelete: ((PhraseEntity) -> Unit)? = null,
    onBack: () -> Unit,
    onMessageShown: () -> Unit
) {
    val editingPhrase = uiState.allPhrases.firstOrNull { it.phrase.id == phraseId }
    val isNew = phraseId == -1L
    val snackbarHostState = remember { SnackbarHostState() }
    var phraseText by rememberSaveable { mutableStateOf("") }
    var memo by rememberSaveable { mutableStateOf("") }
    var selectedCategoryIds by rememberSaveable { mutableStateOf(emptySet<Int>()) }
    var initializedPhraseId by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(editingPhrase?.phrase?.id) {
        if (!isNew && editingPhrase != null && initializedPhraseId != editingPhrase.phrase.id) {
            phraseText = editingPhrase.phrase.text
            memo = editingPhrase.phrase.memo
            selectedCategoryIds = editingPhrase.categories.map { it.id }.toSet()
            initializedPhraseId = editingPhrase.phrase.id
        }
    }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            onMessageShown()
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (isNew) "フレーズ登録" else "フレーズ編集",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = phraseText,
                onValueChange = { phraseText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("フレーズ") },
                minLines = 3
            )
            OutlinedTextField(
                value = memo,
                onValueChange = { memo = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("メモ") },
                minLines = 4
            )
            Text(text = "カテゴリ", style = MaterialTheme.typography.titleMedium)
            CategoryChips(
                categories = uiState.categories,
                selectedIds = selectedCategoryIds,
                onToggle = { categoryId ->
                    selectedCategoryIds =
                        if (categoryId in selectedCategoryIds) {
                            selectedCategoryIds - categoryId
                        } else {
                            selectedCategoryIds + categoryId
                        }
                }
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        if (isNew) {
                            onSaveNew(phraseText, memo, selectedCategoryIds)
                        } else {
                            editingPhrase?.let {
                                onUpdate?.invoke(it.phrase, phraseText, memo, selectedCategoryIds)
                            }
                        }
                    },
                    enabled = phraseText.isNotBlank() && selectedCategoryIds.isNotEmpty()
                ) {
                    Text(if (isNew) "登録" else "更新")
                }
                TextButton(onClick = onBack) {
                    Text("戻る")
                }
                if (!isNew && editingPhrase != null && onDelete != null) {
                    TextButton(onClick = { onDelete(editingPhrase.phrase) }) {
                        Text("削除")
                    }
                }
            }
        }
    }
}
