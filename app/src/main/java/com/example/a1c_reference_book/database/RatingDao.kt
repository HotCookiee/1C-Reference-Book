package com.example.a1c_reference_book.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingDao {

    @Query("SELECT * FROM ratings WHERE articleId = :articleId")
    fun getRatingsByArticle(articleId: Int): Flow<List<Rating>>

    @Query("SELECT COUNT(*) FROM ratings WHERE articleId = :articleId AND isPositive = 1")
    suspend fun getPositiveCount(articleId: Int): Int

    @Query("SELECT COUNT(*) FROM ratings WHERE articleId = :articleId AND isPositive = 0")
    suspend fun getNegativeCount(articleId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRating(rating: Rating)

    @Query("DELETE FROM ratings WHERE articleId = :articleId")
    suspend fun deleteRatingsByArticle(articleId: Int)
}
