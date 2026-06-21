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
import androidx.compose.material3.MaterialTheme
import com.renat.phrasecollection.ui.screen.CardScreen
import com.renat.phrasecollection.ui.screen.EditScreen
import com.renat.phrasecollection.ui.screen.ListScreen
import com.renat.phrasecollection.ui.screen.SettingScreen
import com.renat.phrasecollection.viewmodel.PhraseViewModel


/**
 * アプリ全体の画面遷移を管理するルート画面
 */
@Composable
fun PhraseCollectionApp(viewModel: PhraseViewModel) {

    // Compose Navigationのコントローラ
    val navController = rememberNavController()

    // ViewModelの状態を監視
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 現在表示中の画面情報
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // BottomNavigationに表示するタブ一覧
    val bottomScreens = listOf(
        Screen.List,
        Screen.Register,
        Screen.Card,
        Screen.Setting
    )

    Scaffold(

        // 画面下部のナビゲーションバー
        bottomBar = {

            NavigationBar(

                // Theme.ktで管理する背景色
                containerColor = MaterialTheme.colorScheme.primaryContainer

            ) {

                // タブを生成
                bottomScreens.forEach { screen ->

                    NavigationBarItem(

                        // 現在表示中タブ判定
                        selected = currentDestination
                            ?.hierarchy
                            ?.any { it.route == screen.route } == true,

                        // タブ押下時の画面遷移
                        onClick = {
                            navController.navigate(screen.route) {

                                // 一覧画面を基準に状態保存
                                popUpTo(Screen.List.route) {
                                    saveState = true
                                }

                                // 同一画面多重生成防止
                                launchSingleTop = true

                                // 保存済み状態復元
                                restoreState = true
                            }
                        },

                        // タブ名称
                        label = {
                            Text(
                                when (screen) {
                                    Screen.List -> "一覧"
                                    Screen.Register -> "登録"
                                    Screen.Card -> "カード"
                                    Screen.Setting -> "設定"
                                    Screen.Edit -> ""
                                }
                            )
                        },

                        // タブアイコン
                        icon = {
                            Text(tabIcon(screen))
                        }
                    )
                }
            }
        }

    ) { paddingValues ->

        // 画面遷移定義
        NavHost(
            navController = navController,

            // アプリ起動時の初期画面
            startDestination = Screen.List.route
        ) {

            // 一覧画面
            composable(Screen.List.route) {
                ListScreen(
                    paddingValues = paddingValues,
                    uiState = uiState,
                    onSearchChange = viewModel::updateSearchQuery,
                    onToggleCategory = viewModel::toggleCategoryFilter,
                    onClearCategories = viewModel::clearCategoryFilters,

                    // 編集画面へ遷移
                    onEditClick = { phraseId ->
                        navController.navigate(
                            Screen.Edit.editRoute(phraseId)
                        )
                    },

                    onDeleteClick = viewModel::deletePhrase,
                    onMessageShown = viewModel::clearMessage
                )
            }

            // 新規登録画面
            composable(Screen.Register.route) {
                EditScreen(
                    paddingValues = paddingValues,
                    uiState = uiState,

                    // 新規登録モード
                    phraseId = -1L,

                    onSaveNew = { text, memo, categoryIds ->
                        viewModel.addPhrase(text, memo, categoryIds) {

                            // 登録後は一覧へ戻る
                            navController.navigate(Screen.List.route) {
                                popUpTo(Screen.List.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    },

                    onBack = {
                        navController.navigate(Screen.List.route)
                    },

                    onMessageShown = viewModel::clearMessage
                )
            }

            // 編集画面
            composable(
                route = Screen.Edit.route,
                arguments = listOf(
                    navArgument("phraseId") {
                        type = NavType.LongType
                    }
                )
            ) { entry ->

                // URLパラメータから対象ID取得
                val phraseId =
                    entry.arguments?.getLong("phraseId") ?: -1L

                EditScreen(
                    paddingValues = paddingValues,
                    uiState = uiState,
                    phraseId = phraseId,

                    // 新規保存
                    onSaveNew = { text, memo, categoryIds ->
                        viewModel.addPhrase(
                            text,
                            memo,
                            categoryIds
                        ) {
                            navController.popBackStack()
                        }
                    },

                    // 更新保存
                    onUpdate = { phrase, text, memo, categoryIds ->
                        viewModel.updatePhrase(
                            phrase,
                            text,
                            memo,
                            categoryIds
                        ) {
                            navController.popBackStack()
                        }
                    },

                    // 削除
                    onDelete = { phrase ->
                        viewModel.deletePhrase(phrase)
                        navController.popBackStack()
                    },

                    // 前画面へ戻る
                    onBack = {
                        navController.popBackStack()
                    },

                    onMessageShown = viewModel::clearMessage
                )
            }

            // カード閲覧画面
            composable(Screen.Card.route) {
                CardScreen(
                    paddingValues = paddingValues,
                    categories = uiState.categories,
                    phrases = uiState.allPhrases
                )
            }

            // 設定画面
            composable(Screen.Setting.route) {
                SettingScreen(
                    paddingValues = paddingValues
                )
            }
        }
    }
}

private fun tabIcon(screen: Screen): String =
    when (screen) {
        Screen.List -> "≡"
        Screen.Register -> "+"
        Screen.Card -> "□"
        Screen.Setting -> "i"
        Screen.Edit -> ""
    }