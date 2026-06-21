package com.renat.phrasecollection.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Persisted phrase text and optional memo created by the user.
 */
@Entity(tableName = "phrase")
data class PhraseEntity(
    /** Auto-generated primary key. */
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    /** Favorite English expression. */
    val text: String,
    /** Optional note for meaning, context, or source. */
    val memo: String,
    /** Unix epoch milliseconds when the phrase was created. */
    val createdAt: Long,
    /** Unix epoch milliseconds when the phrase was last updated. */
    val updatedAt: Long
)
