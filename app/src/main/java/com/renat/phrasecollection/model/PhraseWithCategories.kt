package com.renat.phrasecollection.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.renat.phrasecollection.data.entity.CategoryEntity
import com.renat.phrasecollection.data.entity.PhraseCategoryCrossRef
import com.renat.phrasecollection.data.entity.PhraseEntity

/**
 * Read model that returns a phrase with all categories selected for it.
 */
data class PhraseWithCategories(
    /** Phrase row loaded from the phrase table. */
    @Embedded val phrase: PhraseEntity,
    /** Categories connected through the phrase_category join table. */
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PhraseCategoryCrossRef::class,
            parentColumn = "phraseId",
            entityColumn = "categoryId"
        )
    )
    val categories: List<CategoryEntity>
)
