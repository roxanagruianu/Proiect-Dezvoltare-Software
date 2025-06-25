package com.example.myapplication.data.model

class BorrowedBookRepository(private val dao: BorrowedBookDao,) {
    suspend fun insertBorrowedBook(borrowedBook: BorrowedBookEntity) {
        dao.insertBorrowedBook(borrowedBook)
    }

    suspend fun getBorrowedBooksForUser(userId: Int): List<BookEntity> {
        return dao.getBorrowedBooksByUser(userId)
    }
}