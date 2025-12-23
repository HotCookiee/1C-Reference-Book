package com.example.a1c_reference_book.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ratings")
data class Rating(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val articleId: Int,
    val isPositive: Boolean,
    val dateCreated: Long = System.currentTimeMillis()
)
