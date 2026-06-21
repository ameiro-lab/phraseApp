package com.renat.phrasecollection.ui.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.renat.phrasecollection.ui.screen.CardScreen
import com.renat.phrasecollection.ui.screen.EditScreen
import com.renat.phrasecollection.ui.screen.ListScreen
import com.renat.phrasecollection.ui.screen.SettingScreen
import com.renat.phrasecollection.viewmodel.PhraseViewModel

/**
 * Top-level navigation container for all Compose screens.
 */
@Composable
fun PhraseCollectionApp(viewModel: PhraseViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomScreens = listOf(Screen.List, Screen.Card, Screen.Setting)

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomScreens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(Screen.List.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(
                                when (screen) {
                                    Screen.List -> "一覧"
                                    Screen.Card -> "カード"
                                    Screen.Setting -> "設定"
                                    Screen.Edit -> ""
                                }
                            )
                        },
                        icon = { Text(tabIcon(screen)) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.List.route
        ) {
            composable(Screen.List.route) {
                ListScreen(
                    paddingValues = paddingValues,
                    uiState = uiState,
                    onSearchChange = viewModel::updateSearchQuery,
                    onToggleCategory = viewModel::toggleCategoryFilter,
                    onClearCategories = viewModel::clearCategoryFilters,
                    onAddClick = { navController.navigate(Screen.Edit.newRoute()) },
                    onEditClick = { phraseId -> navController.navigate(Screen.Edit.editRoute(phraseId)) },
                    onDeleteClick = viewModel::deletePhrase,
                    onMessageShown = viewModel::clearMessage
                )
            }
            composable(
                route = Screen.Edit.route,
                arguments = listOf(navArgument("phraseId") { type = NavType.LongType })
            ) { entry ->
                val phraseId = entry.arguments?.getLong("phraseId") ?: -1L
                EditScreen(
                    paddingValues = paddingValues,
                    uiState = uiState,
                    phraseId = phraseId,
                    onSaveNew = { text, memo, categoryIds ->
                        viewModel.addPhrase(text, memo, categoryIds) { navController.popBackStack() }
                    },
                    onUpdate = { phrase, text, memo, categoryIds ->
                        viewModel.updatePhrase(phrase, text, memo, categoryIds) { navController.popBackStack() }
                    },
                    onDelete = { phrase ->
                        viewModel.deletePhrase(phrase)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() },
                    onMessageShown = viewModel::clearMessage
                )
            }
            composable(Screen.Card.route) {
                CardScreen(paddingValues = paddingValues, phrases = uiState.phrases)
            }
            composable(Screen.Setting.route) {
                SettingScreen(paddingValues = paddingValues)
            }
        }
    }
}

/**
 * Returns compact text symbols for bottom navigation items.
 */
private fun tabIcon(screen: Screen): String =
    when (screen) {
        Screen.List -> "≡"
        Screen.Card -> "□"
        Screen.Setting -> "i"
        Screen.Edit -> ""
    }
