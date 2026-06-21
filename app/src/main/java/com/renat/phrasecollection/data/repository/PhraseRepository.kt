package com.renat.phrasecollection.data.repository

import com.renat.phrasecollection.data.dao.PhraseDao
import com.renat.phrasecollection.data.entity.CategoryEntity
import com.renat.phrasecollection.data.entity.PhraseEntity
import com.renat.phrasecollection.model.FixedCategory
import com.renat.phrasecollection.model.PhraseWithCategories
import kotlinx.coroutines.flow.Flow

/**
 * Repository that exposes local phrase operations to the ViewModel.
 */
class PhraseRepository(private val phraseDao: PhraseDao) {
    /** Observes all fixed categories. */
    val categories: Flow<List<CategoryEntity>> = phraseDao.getCategories()

    /** Observes all saved phrases. */
    fun getAllPhrases(): Flow<List<PhraseWithCategories>> = phraseDao.getAllPhrases()

    /** Searches saved phrases by text and memo. */
    fun searchPhrases(query: String): Flow<List<PhraseWithCategories>> = phraseDao.searchPhrases(query)

    /** Observes a single phrase by id. */
    fun getPhraseById(id: Long): Flow<PhraseWithCategories?> = phraseDao.getPhraseById(id)

    /** Saves a new phrase and its selected categories. */
    suspend fun addPhrase(text: String, memo: String, categoryIds: Set<Int>) {
        val now = System.currentTimeMillis()
        phraseDao.insertPhraseWithCategories(
            phrase = PhraseEntity(text = text.trim(), memo = memo.trim(), createdAt = now, updatedAt = now),
            categoryIds = categoryIds
        )
    }

    /** Updates an existing phrase while preserving its original creation timestamp. */
    suspend fun updatePhrase(existing: PhraseEntity, text: String, memo: String, categoryIds: Set<Int>) {
        phraseDao.updatePhraseWithCategories(
            phrase = existing.copy(text = text.trim(), memo = memo.trim(), updatedAt = System.currentTimeMillis()),
            categoryIds = categoryIds
        )
    }

    /** Deletes a phrase and all links through cascade behavior. */
    suspend fun deletePhrase(phrase: PhraseEntity) {
        phraseDao.deletePhrase(phrase)
    }

    /** Ensures fixed categories exist after app start or database restore. */
    suspend fun seedCategories() {
        phraseDao.insertCategories(FixedCategory.entities())
    }
}
