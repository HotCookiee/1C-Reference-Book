package com.example.a1c_reference_book.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.database.Article
import com.example.a1c_reference_book.database.Favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleDetailViewModel(
    private val database: AppDatabase,
    private val articleId: Int
) : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> = _article

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    init {
        loadArticle()
        checkFavoriteStatus()
    }

    private fun loadArticle() {
        viewModelScope.launch {
            val art = database.articleDao().getArticleById(articleId)
            _article.value = art
        }
    }

    private fun checkFavoriteStatus() {
        viewModelScope.launch {
            val count = database.favoriteDao().isFavorite(articleId)
            _isFavorite.value = count > 0
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            if (_isFavorite.value) {
                database.favoriteDao().deleteFavorite(articleId)
                _isFavorite.value = false
            } else {
                database.favoriteDao().insertFavorite(Favorite(articleId = articleId))
                _isFavorite.value = true
            }
        }
    }
}
