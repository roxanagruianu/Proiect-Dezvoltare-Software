package com.example.myapplication.data.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks(): LiveData<List<BookEntity>>

    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteBookById(bookId: Int)

    @Query("SELECT * FROM books WHERE title = :title AND author = :author LIMIT 1")
    suspend fun getBookByTitleAndAuthor(title: String, author: String): BookEntity?

    @Transaction
    @Query("""
        SELECT b.* FROM books b
        INNER JOIN borrowed_books bb ON bb.bookId = b.id
        WHERE bb.userId = :userId
    """)
    suspend fun getBorrowedBooksByUser(userId: Int): List<BookEntity>
}