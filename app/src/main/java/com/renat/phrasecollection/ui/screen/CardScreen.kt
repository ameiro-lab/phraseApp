package com.renat.phrasecollection.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.renat.phrasecollection.model.PhraseWithCategories
import kotlin.random.Random

/**
 * Displays saved phrases as flippable cards with previous, next, and random controls.
 */
@Composable
fun CardScreen(
    paddingValues: PaddingValues,
    phrases: List<PhraseWithCategories>
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var showBack by remember { mutableStateOf(false) }

    LaunchedEffect(phrases.size) {
        if (phrases.isEmpty()) {
            currentIndex = 0
            showBack = false
        } else if (currentIndex > phrases.lastIndex) {
            currentIndex = phrases.lastIndex
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
        if (phrases.isEmpty()) {
            Text("カード表示できるフレーズがありません。")
            return@Column
        }

        val current = phrases[currentIndex]
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 260.dp)
                .clickable { showBack = !showBack },
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
                            text = current.phrase.memo.ifBlank { "メモはありません。" },
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = current.categories.joinToString(" / ") { it.name },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Text(
                        text = current.phrase.text,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Text(
            text = "${currentIndex + 1} / ${phrases.size}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    currentIndex = if (currentIndex == 0) phrases.lastIndex else currentIndex - 1
                    showBack = false
                }
            ) {
                Text("前へ")
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    currentIndex = if (currentIndex == phrases.lastIndex) 0 else currentIndex + 1
                    showBack = false
                }
            ) {
                Text("次へ")
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                currentIndex = Random.nextInt(phrases.size)
                showBack = false
            }
        ) {
            Text("ランダム表示")
        }
        Text(
            text = "カードをタップすると表面と裏面を切り替えます。",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
