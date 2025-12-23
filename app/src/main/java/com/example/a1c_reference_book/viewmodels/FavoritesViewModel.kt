package com.example.a1c_reference_book.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.database.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(private val database: AppDatabase) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Article>>(emptyList())
    val favorites: StateFlow<List<Article>> = _favorites

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            database.favoriteDao().getFavoriteArticles().collect { favoritesList ->
                _favorites.value = favoritesList
            }
        }
    }
}
