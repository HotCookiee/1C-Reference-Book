package com.example.a1c_reference_book.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT articleId FROM favorites")
    fun getFavoriteArticleIds(): Flow<List<Int>>

    @Query("""
        SELECT articles.* FROM articles 
        INNER JOIN favorites ON articles.id = favorites.articleId
        ORDER BY articles.title ASC
    """)
    fun getFavoriteArticles(): Flow<List<Article>>

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE articleId = :articleId")
    suspend fun deleteFavorite(articleId: Int)

    @Query("SELECT COUNT(*) FROM favorites WHERE articleId = :articleId")
    suspend fun isFavorite(articleId: Int): Int
}
