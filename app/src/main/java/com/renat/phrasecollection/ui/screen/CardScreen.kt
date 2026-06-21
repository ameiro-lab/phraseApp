package com.renat.phrasecollection.ui.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.renat.phrasecollection.data.entity.CategoryEntity
import com.renat.phrasecollection.ui.component.CategoryChips
import com.renat.phrasecollection.model.PhraseWithCategories

/**
 * Displays saved phrases as flippable study cards with category filtering and vertical swipes.
 */
@Composable
fun CardScreen(
    paddingValues: PaddingValues,
    categories: List<CategoryEntity>,
    phrases: List<PhraseWithCategories>
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var showBack by remember { mutableStateOf(false) }
    var selectedCategoryIds by rememberSaveable { mutableStateOf(emptySet<Int>()) }
    val filteredPhrases = remember(phrases, selectedCategoryIds) {
        if (selectedCategoryIds.isEmpty()) {
            phrases
        } else {
            phrases.filter { phrase ->
                phrase.categories.any { category -> category.id in selectedCategoryIds }
            }
        }
    }

    LaunchedEffect(filteredPhrases.size, selectedCategoryIds) {
        if (filteredPhrases.isEmpty()) {
            currentIndex = 0
            showBack = false
        } else if (currentIndex > filteredPhrases.lastIndex) {
            currentIndex = filteredPhrases.lastIndex
            showBack = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "カード",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        CategoryChips(
            categories = categories,
            selectedIds = selectedCategoryIds,
            onToggle = { categoryId ->
                selectedCategoryIds =
                    if (categoryId in selectedCategoryIds) {
                        selectedCategoryIds - categoryId
                    } else {
                        selectedCategoryIds + categoryId
                    }
            }
        )
        if (filteredPhrases.isEmpty()) {
            Text("カード表示できるフレーズがありません。")
            return@Column
        }

        val current = filteredPhrases[currentIndex]
        var dragAmount by remember { mutableStateOf(0f) }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .heightIn(min = 360.dp)
                .pointerInput(filteredPhrases.size, currentIndex) {
                    detectTapGestures(onTap = { showBack = !showBack })
                }
                .pointerInput(filteredPhrases.size, currentIndex) {
                    detectVerticalDragGestures(
                        onDragStart = { dragAmount = 0f },
                        onVerticalDrag = { _, dragDelta -> dragAmount += dragDelta },
                        onDragEnd = {
                            when {
                                dragAmount < -80f -> {
                                    currentIndex =
                                        if (currentIndex == filteredPhrases.lastIndex) 0 else currentIndex + 1
                                    showBack = false
                                }
                                dragAmount > 80f -> {
                                    currentIndex =
                                        if (currentIndex == 0) filteredPhrases.lastIndex else currentIndex - 1
                                    showBack = false
                                }
                            }
                            dragAmount = 0f
                        }
                    )
                },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                if (showBack) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = "意味",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = current.phrase.memo.ifBlank { "メモはありません。" },
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = current.categories.joinToString(" / ") { it.name },
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Text(
                        text = current.phrase.text,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontFamily = FontFamily.Serif,
                            letterSpacing = 0.3.sp
                        ),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Text(
            text = "${currentIndex + 1} / ${filteredPhrases.size}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "タップで問題/を意味を切り替え。上スワイプで次、下スワイプで前。",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
