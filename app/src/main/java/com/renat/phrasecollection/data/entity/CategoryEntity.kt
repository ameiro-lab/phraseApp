package com.renat.phrasecollection.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Fixed category master row for grouping collected phrases.
 */
@Entity(tableName = "category")
data class CategoryEntity(
    /** Stable category id defined by the app source code. */
    @PrimaryKey val id: Int,
    /** Display name shown in filters, forms, and cards. */
    val name: String
)
