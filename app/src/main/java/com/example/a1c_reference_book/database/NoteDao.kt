package com.example.a1c_reference_book.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE articleId = :articleId ORDER BY dateCreated DESC")
    fun getNotesByArticle(articleId: Int): Flow<List<Note>>

    @Query("SELECT * FROM notes ORDER BY dateCreated DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNote(noteId: Int)
}
