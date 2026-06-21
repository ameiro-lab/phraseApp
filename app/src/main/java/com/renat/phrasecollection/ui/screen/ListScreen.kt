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
 * Shows saved phrases with search, category filters, edit, delete, and add actions.
 */
@Composable
fun ListScreen(
    paddingValues: PaddingValues,
    uiState: PhraseUiState,
    onSearchChange: (String) -> Unit,
    onToggleCategory: (Int) -> Unit,
    onClearCategories: () -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (PhraseEntity) -> Unit,
    onMessageShown: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            onMessageShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            Button(onClick = onAddClick) {
                Text("新規登録")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Phrase Collection App",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = onAddClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("フレーズを登録")
                    }
                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = onSearchChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("検索") },
                        placeholder = { Text("フレーズまたはメモ") }
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "カテゴリ",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.titleSmall
                        )
                        if (uiState.selectedCategoryIds.isNotEmpty()) {
                            TextButton(onClick = onClearCategories) {
                                Text("解除")
                            }
                        }
                    }
                    CategoryChips(
                        categories = uiState.categories,
                        selectedIds = uiState.selectedCategoryIds,
                        onToggle = onToggleCategory
                    )
                }
            }
            if (uiState.phrases.isEmpty()) {
                item {
                    Text(
                        text = "保存済みのフレーズはありません。",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                items(uiState.phrases, key = { it.phrase.id }) { phrase ->
                    PhraseListItem(
                        phrase = phrase,
                        onEditClick = onEditClick,
                        onDeleteClick = { onDeleteClick(phrase.phrase) }
                    )
                }
            }
        }
    }
}
