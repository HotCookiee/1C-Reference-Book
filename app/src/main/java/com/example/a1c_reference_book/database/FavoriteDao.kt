package com.example.a1c_reference_book.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("""
        SELECT a.* FROM articles a 
        INNER JOIN favorites f ON a.id = f.articleId 
        ORDER BY f.dateAdded DESC
    """)
    fun getFavoriteArticles(): Flow<List<Article>>

    @Query("SELECT COUNT(*) FROM favorites WHERE articleId = :articleId")
    suspend fun isFavorite(articleId: Int): Int

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE articleId = :articleId")
    suspend fun deleteFavorite(articleId: Int)

    @Query("DELETE FROM favorites")
    suspend fun deleteAllFavorites()
}
