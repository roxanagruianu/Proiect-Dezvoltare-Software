package com.example.myapplication.data.model

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {
    suspend fun addBook(book: BookEntity) = bookDao.insertBook(book)
    suspend fun getAllBooks(): List<BookEntity> = bookDao.getAllBooks()
    suspend fun deleteBookById(id: Int) = bookDao.deleteBookById(id)
}
