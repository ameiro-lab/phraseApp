package com.renat.phrasecollection.model

import com.renat.phrasecollection.data.entity.CategoryEntity

/**
 * Source-defined category master values for version 1.0.
 */
enum class FixedCategory(val id: Int, val displayName: String) {
    /** Everyday conversation phrases. */
    DailyConversation(1, "日常会話"),

    /** Poetic or literary expressions. */
    Poetry(2, "詩"),

    /** Grammar-focused expressions. */
    Grammar(3, "文法");

    companion object {
        /** Converts all fixed categories to Room entities for database seeding. */
        fun entities(): List<CategoryEntity> = entries.map { CategoryEntity(it.id, it.displayName) }
    }
}
