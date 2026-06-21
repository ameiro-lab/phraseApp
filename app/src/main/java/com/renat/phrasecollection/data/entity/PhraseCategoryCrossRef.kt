package com.renat.phrasecollection.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Join table that connects phrases and fixed categories with a many-to-many relation.
 */
@Entity(
    tableName = "phrase_category",
    primaryKeys = ["phraseId", "categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = PhraseEntity::class,
            parentColumns = ["id"],
            childColumns = ["phraseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("phraseId"), Index("categoryId")]
)
data class PhraseCategoryCrossRef(
    /** Referenced phrase id. */
    val phraseId: Long,
    /** Referenced category id. */
    val categoryId: Int
)
