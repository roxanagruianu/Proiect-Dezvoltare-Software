package com.example.myapplication.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface BorrowedBookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBorrowedBook(borrowedBook: BorrowedBookEntity)

    @Query("SELECT * FROM borrowed_books WHERE userId = :userId")
    suspend fun getBooksForUser(userId: Int): List<BorrowedBookEntity>

    @Transaction
    @Query("""
        SELECT b.* FROM books b
        INNER JOIN borrowed_books bb ON bb.bookId = b.id
        WHERE bb.userId = :userId
    """)
    suspend fun getBorrowedBooksByUser(userId: Int): List<BookEntity>

    @Update
    suspend fun updateBorrowedBook(borrowedBook: BorrowedBookEntity)

    @Query("SELECT * FROM borrowed_books WHERE userId = :userId")
    suspend fun getBorrowedBooksByUserDi(userId: Int): List<BorrowedBookEntity>
}
