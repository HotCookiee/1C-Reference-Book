package com.example.a1c_reference_book.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a1c_reference_book.components.ArticleCard
import com.example.a1c_reference_book.components.SearchBar
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.viewmodels.ArticlesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    database: AppDatabase,
    categoryId: Int,
    onArticleClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel = remember { ArticlesViewModel(database, categoryId) }
    val articles by viewModel.articles.collectAsState()
    val category by viewModel.category.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(category?.name ?: "Статьи") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.onSearchQueryChange(it) }
            )

            if (articles.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isEmpty()) "Загрузка..." else "Ничего не найдено",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(articles) { article ->
                        ArticleCard(
                            article = article,
                            onClick = { onArticleClick(article.id) }
                        )
                    }
                }
            }
        }
    }
}
