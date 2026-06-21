package com.renat.phrasecollection.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.renat.phrasecollection.data.entity.CategoryEntity

/**
 * Multi-select category chips used by filters and edit forms.
 */
@Composable
fun CategoryChips(
    categories: List<CategoryEntity>,
    selectedIds: Set<Int>,
    onToggle: (Int) -> Unit
) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        categories.forEach { category ->
            FilterChip(
                selected = category.id in selectedIds,
                onClick = { onToggle(category.id) },
                label = { Text(category.name) }
            )
        }
    }
}
