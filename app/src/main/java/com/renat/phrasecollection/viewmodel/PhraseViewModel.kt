package com.renat.phrasecollection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renat.phrasecollection.data.entity.PhraseEntity
import com.renat.phrasecollection.data.repository.PhraseRepository
import com.renat.phrasecollection.model.PhraseWithCategories
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel that manages phrase list, search, filters, edits, and deletion.
 */
class PhraseViewModel(private val repository: PhraseRepository) : ViewModel() {
    private val searchQuery = MutableStateFlow("")
    private val selectedCategoryIds = MutableStateFlow<Set<Int>>(emptySet())
    private val message = MutableStateFlow<String?>(null)

    /** Screen state observed by Compose UI. */
    val uiState: StateFlow<PhraseUiState> = combine(
        searchQuery,
        selectedCategoryIds,
        repository.categories,
        repository.getAllPhrases(),
        message
    ) { query, selectedIds, categories, allPhrases, currentMessage ->
        val searchedPhrases = filterBySearch(allPhrases, query)
        PhraseUiState(
            searchQuery = query,
            selectedCategoryIds = selectedIds,
            categories = categories,
            phrases = filterByCategories(searchedPhrases, selectedIds),
            allPhrases = allPhrases,
            message = currentMessage
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PhraseUiState())

    init {
        viewModelScope.launch { repository.seedCategories() }
    }

    /** Updates the search text. */
    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    /** Toggles a category filter chip in the list screen. */
    fun toggleCategoryFilter(categoryId: Int) {
        selectedCategoryIds.update { ids ->
            if (categoryId in ids) ids - categoryId else ids + categoryId
        }
    }

    /** Clears all selected category filters. */
    fun clearCategoryFilters() {
        selectedCategoryIds.value = emptySet()
    }

    /** Saves a new phrase after validation. */
    fun addPhrase(text: String, memo: String, categoryIds: Set<Int>, onComplete: () -> Unit) {
        if (!validate(text, categoryIds)) return
        viewModelScope.launch {
            repository.addPhrase(text, memo, categoryIds)
            onComplete()
        }
    }

    /** Updates an existing phrase after validation. */
    fun updatePhrase(
        existing: PhraseEntity,
        text: String,
        memo: String,
        categoryIds: Set<Int>,
        onComplete: () -> Unit
    ) {
        if (!validate(text, categoryIds)) return
        viewModelScope.launch {
            repository.updatePhrase(existing, text, memo, categoryIds)
            onComplete()
        }
    }

    /** Deletes a phrase. */
    fun deletePhrase(phrase: PhraseEntity) {
        viewModelScope.launch { repository.deletePhrase(phrase) }
    }

    /** Clears the current transient message. */
    fun clearMessage() {
        message.value = null
    }

    /** Returns a phrase from the current state by id when available. */
    fun findPhraseInState(phraseId: Long): PhraseWithCategories? =
        uiState.value.allPhrases.firstOrNull { it.phrase.id == phraseId }

    private fun validate(text: String, categoryIds: Set<Int>): Boolean {
        return when {
            text.isBlank() -> {
                message.value = "フレーズを入力してください。"
                false
            }
            categoryIds.isEmpty() -> {
                message.value = "カテゴリを1つ以上選択してください。"
                false
            }
            else -> true
        }
    }

    private fun filterByCategories(
        phrases: List<PhraseWithCategories>,
        selectedIds: Set<Int>
    ): List<PhraseWithCategories> {
        if (selectedIds.isEmpty()) return phrases
        return phrases.filter { phrase ->
            phrase.categories.any { category -> category.id in selectedIds }
        }
    }

    private fun filterBySearch(
        phrases: List<PhraseWithCategories>,
        query: String
    ): List<PhraseWithCategories> {
        val keyword = query.trim()
        if (keyword.isEmpty()) return phrases
        return phrases.filter { phrase ->
            phrase.phrase.text.contains(keyword, ignoreCase = true) ||
                phrase.phrase.memo.contains(keyword, ignoreCase = true)
        }
    }
}
