package com.example.a1c_reference_book.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.database.Article
import com.example.a1c_reference_book.database.Favorite
import com.example.a1c_reference_book.database.Note
import com.example.a1c_reference_book.database.Rating
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

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private val _positiveRatings = MutableStateFlow(0)
    val positiveRatings: StateFlow<Int> = _positiveRatings

    private val _negativeRatings = MutableStateFlow(0)
    val negativeRatings: StateFlow<Int> = _negativeRatings

    init {
        loadArticle()
        checkFavoriteStatus()
        incrementViewCount()
        loadNotes()
        loadRatings()
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

    private fun incrementViewCount() {
        viewModelScope.launch {
            database.articleDao().incrementViewCount(articleId)
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {
            database.noteDao().getNotesByArticle(articleId).collect { notesList ->
                _notes.value = notesList
            }
        }
    }

    private fun loadRatings() {
        viewModelScope.launch {
            _positiveRatings.value = database.ratingDao().getPositiveCount(articleId)
            _negativeRatings.value = database.ratingDao().getNegativeCount(articleId)
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

    fun addNote(text: String) {
        viewModelScope.launch {
            val note = Note(articleId = articleId, noteText = text)
            database.noteDao().insertNote(note)
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            database.noteDao().deleteNote(noteId)
        }
    }

    fun addRating(isPositive: Boolean) {
        viewModelScope.launch {
            database.ratingDao().deleteRatingsByArticle(articleId)
            val rating = Rating(articleId = articleId, isPositive = isPositive)
            database.ratingDao().insertRating(rating)
            loadRatings()
        }
    }
}
