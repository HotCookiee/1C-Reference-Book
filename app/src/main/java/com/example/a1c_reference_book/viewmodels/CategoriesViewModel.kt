package com.example.a1c_reference_book.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.database.Article
import com.example.a1c_reference_book.database.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel(private val database: AppDatabase) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _topArticles = MutableStateFlow<List<Article>>(emptyList())
    val topArticles: StateFlow<List<Article>> = _topArticles

    init {
        loadCategories()
        loadTopArticles()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            database.categoryDao().getAllCategories().collect { categoriesList ->
                _categories.value = categoriesList
            }
        }
    }

    private fun loadTopArticles() {
        viewModelScope.launch {
            database.articleDao().getTopArticles(25).collect { articles ->
                _topArticles.value = articles
            }
        }
    }
}
