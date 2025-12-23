package com.example.a1c_reference_book.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val articleId: Int,
    val noteText: String,
    val dateCreated: Long = System.currentTimeMillis()
)
