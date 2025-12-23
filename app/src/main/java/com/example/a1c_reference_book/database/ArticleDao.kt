package com.example.a1c_reference_book.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles WHERE categoryId = :categoryId ORDER BY title ASC")
    fun getArticlesByCategory(categoryId: Int): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE id = :articleId")
    suspend fun getArticleById(articleId: Int): Article?

    @Query("SELECT * FROM articles WHERE title LIKE '%' || :searchQuery || '%' ORDER BY title ASC")
    fun searchArticles(searchQuery: String): Flow<List<Article>>

    @Query("SELECT * FROM articles ORDER BY title ASC")
    fun getAllArticles(): Flow<List<Article>>

    @Query("SELECT * FROM articles ORDER BY viewCount DESC LIMIT :limit")
    fun getTopArticles(limit: Int): Flow<List<Article>>

    @Query("UPDATE articles SET viewCount = viewCount + 1 WHERE id = :articleId")
    suspend fun incrementViewCount(articleId: Int)

    @Insert
    suspend fun insertArticle(article: Article)
}
