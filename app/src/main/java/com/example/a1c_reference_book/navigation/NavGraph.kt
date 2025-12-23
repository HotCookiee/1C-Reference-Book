package com.example.a1c_reference_book.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.screens.ArticleDetailScreen
import com.example.a1c_reference_book.screens.ArticlesScreen
import com.example.a1c_reference_book.screens.CategoriesScreen
import com.example.a1c_reference_book.screens.FavoritesScreen
import com.example.a1c_reference_book.screens.SettingsScreen
import com.example.a1c_reference_book.viewmodels.ThemeViewModel

sealed class Screen(val route: String) {
    object Categories : Screen("categories")
    object Articles : Screen("articles/{categoryId}") {
        fun createRoute(categoryId: Int) = "articles/$categoryId"
    }
    object ArticleDetail : Screen("article_detail/{articleId}") {
        fun createRoute(articleId: Int) = "article_detail/$articleId"
    }
    object Favorites : Screen("favorites")
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    database: AppDatabase,
    themeViewModel: ThemeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Categories.route
    ) {

        composable(route = Screen.Categories.route) {
            CategoriesScreen(
                database = database,
                onCategoryClick = { categoryId ->
                    navController.navigate(Screen.Articles.createRoute(categoryId))
                },
                onFavoritesClick = {
                    navController.navigate(Screen.Favorites.route)
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onArticleClick = { articleId ->
                    navController.navigate(Screen.ArticleDetail.createRoute(articleId))
                }
            )
        }

        composable(
            route = Screen.Articles.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0

            ArticlesScreen(
                database = database,
                categoryId = categoryId,
                onArticleClick = { articleId ->
                    navController.navigate(Screen.ArticleDetail.createRoute(articleId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.ArticleDetail.route,
            arguments = listOf(
                navArgument("articleId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getInt("articleId") ?: 0

            ArticleDetailScreen(
                database = database,
                articleId = articleId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                database = database,
                onArticleClick = { articleId ->
                    navController.navigate(Screen.ArticleDetail.createRoute(articleId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(
                database = database,
                themeViewModel = themeViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
