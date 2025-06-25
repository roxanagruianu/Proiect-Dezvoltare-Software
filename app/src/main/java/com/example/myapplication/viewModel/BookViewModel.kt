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

    val allBooks: LiveData<List<BookEntity>> = repository.allBooks

    fun deleteBookById(bookId: Int) {
        viewModelScope.launch {
            repository.deleteBookById(bookId)
        }
    }
}
