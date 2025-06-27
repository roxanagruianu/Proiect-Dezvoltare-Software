package com.example.myapplication.data.model

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {
    suspend fun addBook(book: BookEntity) = bookDao.insertBook(book)
    val allBooks: LiveData<List<BookEntity>> = bookDao.getAllBooks()
    suspend fun deleteBookById(id: Int) = bookDao.deleteBookById(id)
    suspend fun getBookById(bookId: Int): BookEntity? {
        return bookDao.getBookById(bookId)
    }
}
