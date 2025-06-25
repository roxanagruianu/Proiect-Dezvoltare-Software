package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.BookEntity
import com.example.myapplication.data.model.BorrowedBookEntity
import com.example.myapplication.data.model.BorrowedBookRepository
import com.example.myapplication.data.model.UserRepository
import kotlinx.coroutines.launch

class BorrowedBookViewModel(private val repository: BorrowedBookRepository,
                            private val userRepository: UserRepository
) : ViewModel() {

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

    private val _borrowedBooks = MutableLiveData<List<BookEntity>>()
    val borrowedBooks: LiveData<List<BookEntity>> = _borrowedBooks

    fun loadBorrowedBooks(email: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email)
            user?.let {
                val books = repository.getBorrowedBooksForUser(it.id)
                _borrowedBooks.postValue(books)
            }
        }
    }
}