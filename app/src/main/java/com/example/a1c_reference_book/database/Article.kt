package com.example.a1c_reference_book.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryId: Int,
    val title: String,
    val content: String,
    val dateAdded: Long = System.currentTimeMillis(),
    val viewCount: Int = 0
)
