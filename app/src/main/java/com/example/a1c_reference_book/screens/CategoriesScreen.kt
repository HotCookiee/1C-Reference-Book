package com.example.a1c_reference_book.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a1c_reference_book.components.CategoryCard
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.viewmodels.CategoriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    database: AppDatabase,
    onCategoryClick: (Int) -> Unit,
    onFavoritesClick: () -> Unit
) {
    val viewModel = remember { CategoriesViewModel(database) }
    val categories by viewModel.categories.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Справочник 1С") },
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Избранное"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            if (categories.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            } else {
                items(categories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = { onCategoryClick(category.id) }
                    )
                }
            }
        }
    }
}
