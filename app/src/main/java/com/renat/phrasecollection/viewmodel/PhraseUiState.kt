package com.renat.phrasecollection.viewmodel

import com.renat.phrasecollection.data.entity.CategoryEntity
import com.renat.phrasecollection.model.PhraseWithCategories

/**
 * Immutable screen state shared by the phrase collection screens.
 */
data class PhraseUiState(
    /** Current search text applied to phrase text and memo. */
    val searchQuery: String = "",
    /** Category ids selected as active filters. */
    val selectedCategoryIds: Set<Int> = emptySet(),
    /** Fixed category master rows. */
    val categories: List<CategoryEntity> = emptyList(),
    /** Phrases visible after search and category filtering. */
    val phrases: List<PhraseWithCategories> = emptyList(),
    /** All saved phrases without the current list filters. */
    val allPhrases: List<PhraseWithCategories> = emptyList(),
    /** User-facing transient error message. */
    val message: String? = null
)
