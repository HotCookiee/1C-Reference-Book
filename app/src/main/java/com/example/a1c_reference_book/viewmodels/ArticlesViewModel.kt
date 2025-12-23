package com.example.a1c_reference_book.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.database.Article
import com.example.a1c_reference_book.database.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticlesViewModel(
    private val database: AppDatabase,
    private val categoryId: Int
) : ViewModel() {

    private val _category = MutableStateFlow<Category?>(null)
    val category: StateFlow<Category?> = _category

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var allArticles: List<Article> = emptyList()

    init {
        loadCategory()
        loadArticles()
    }

    private fun loadCategory() {
        viewModelScope.launch {
            val cat = database.categoryDao().getCategoryById(categoryId)
            _category.value = cat
        }
    }

    private fun loadArticles() {
        viewModelScope.launch {
            database.articleDao().getArticlesByCategory(categoryId).collect { articlesList ->
                allArticles = articlesList
                filterArticles()
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterArticles()
    }

    private fun filterArticles() {
        val query = _searchQuery.value
        _articles.value = if (query.isEmpty()) {
            allArticles
        } else {
            allArticles.filter { article ->
                article.title.contains(query, ignoreCase = true) ||
                        article.content.contains(query, ignoreCase = true)
            }
        }
    }
}
