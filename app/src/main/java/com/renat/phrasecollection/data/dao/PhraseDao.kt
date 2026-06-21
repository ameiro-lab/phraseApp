package com.renat.phrasecollection.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.renat.phrasecollection.data.entity.CategoryEntity
import com.renat.phrasecollection.data.entity.PhraseCategoryCrossRef
import com.renat.phrasecollection.data.entity.PhraseEntity
import com.renat.phrasecollection.model.PhraseWithCategories
import kotlinx.coroutines.flow.Flow

/**
 * Data access object for phrase, category, and phrase-category persistence.
 */
@Dao
interface PhraseDao {
    /** Inserts a phrase and returns the generated phrase id. */
    @Insert
    suspend fun insertPhrase(phrase: PhraseEntity): Long

    /** Updates the editable fields and updated timestamp of a phrase. */
    @Update
    suspend fun updatePhrase(phrase: PhraseEntity)

    /** Deletes a phrase and cascades its category links. */
    @Delete
    suspend fun deletePhrase(phrase: PhraseEntity)

    /** Loads one phrase with categories by id. */
    @Transaction
    @Query("SELECT * FROM phrase WHERE id = :phraseId")
    fun getPhraseById(phraseId: Long): Flow<PhraseWithCategories?>

    /** Observes all phrases with categories ordered by most recently updated first. */
    @Transaction
    @Query("SELECT * FROM phrase ORDER BY updatedAt DESC")
    fun getAllPhrases(): Flow<List<PhraseWithCategories>>

    /** Searches phrase text and memo by partial match. */
    @Transaction
    @Query(
        """
        SELECT * FROM phrase
        WHERE text LIKE '%' || :query || '%'
           OR memo LIKE '%' || :query || '%'
        ORDER BY updatedAt DESC
        """
    )
    fun searchPhrases(query: String): Flow<List<PhraseWithCategories>>

    /** Inserts fixed category master rows without replacing existing data. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    /** Observes all fixed categories. */
    @Query("SELECT * FROM category ORDER BY id ASC")
    fun getCategories(): Flow<List<CategoryEntity>>

    /** Inserts category links for a phrase. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhraseCategoryCrossRefs(crossRefs: List<PhraseCategoryCrossRef>)

    /** Removes all category links for a phrase before rebuilding them. */
    @Query("DELETE FROM phrase_category WHERE phraseId = :phraseId")
    suspend fun deleteCrossRefsForPhrase(phraseId: Long)

    /** Inserts a phrase and category links in one Room transaction. */
    @Transaction
    suspend fun insertPhraseWithCategories(phrase: PhraseEntity, categoryIds: Set<Int>): Long {
        val phraseId = insertPhrase(phrase)
        replacePhraseCategories(phraseId, categoryIds)
        return phraseId
    }

    /** Updates a phrase and replaces category links in one Room transaction. */
    @Transaction
    suspend fun updatePhraseWithCategories(phrase: PhraseEntity, categoryIds: Set<Int>) {
        updatePhrase(phrase)
        replacePhraseCategories(phrase.id, categoryIds)
    }

    /** Rebuilds all category links for a phrase. */
    suspend fun replacePhraseCategories(phraseId: Long, categoryIds: Set<Int>) {
        deleteCrossRefsForPhrase(phraseId)
        insertPhraseCategoryCrossRefs(
            categoryIds.map { categoryId ->
                PhraseCategoryCrossRef(phraseId = phraseId, categoryId = categoryId)
            }
        )
    }
}
