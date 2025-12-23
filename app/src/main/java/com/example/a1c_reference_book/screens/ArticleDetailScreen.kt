package com.example.a1c_reference_book.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.viewmodels.ArticleDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    database: AppDatabase,
    articleId: Int,
    onBackClick: () -> Unit
) {
    val viewModel = remember { ArticleDetailViewModel(database, articleId) }
    val article by viewModel.article.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали статьи") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleFavorite() }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Убрать из избранного" else "Добавить в избранное",
                            tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        article?.let { art ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = art.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Divider(modifier = Modifier.padding(bottom = 16.dp))

                Text(
                    text = art.content,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
