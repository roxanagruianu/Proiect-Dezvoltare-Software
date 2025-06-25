package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.BorrowedBookEntity
import com.example.myapplication.data.model.BorrowedBookRepository
import kotlinx.coroutines.launch

class BorrowedBookViewModel(private val repository: BorrowedBookRepository) : ViewModel() {

    fun borrowBook(userId: Int, bookId: Int) {
        viewModelScope.launch {
            val borrowedBook = BorrowedBookEntity(
                userId = userId,
                bookId = bookId,
                borrowedAt = System.currentTimeMillis()
            )
            repository.insertBorrowedBook(borrowedBook)
        }
    }
}