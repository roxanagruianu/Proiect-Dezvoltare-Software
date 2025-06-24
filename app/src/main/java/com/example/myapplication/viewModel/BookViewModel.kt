package com.example.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.BookEntity
import com.example.myapplication.data.model.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    fun addBook(book: BookEntity) {
        viewModelScope.launch {
            repository.addBook(book)
        }
    }

    fun getAllBooks() {
        viewModelScope.launch {
            val books = repository.getAllBooks()
            books.forEach {
                Log.d("BookList", "Carte: ${it.title} de ${it.author} - ${it.description}")
            }
        }
    }

    fun deleteBookById(bookId: Int) {
        viewModelScope.launch {
            repository.deleteBookById(bookId)
        }
    }
}
