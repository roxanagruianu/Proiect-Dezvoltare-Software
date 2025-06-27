package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.BookEntity
import com.example.myapplication.data.model.BookRepository
import com.example.myapplication.data.model.BorrowedBookDisplay
import com.example.myapplication.data.model.BorrowedBookEntity
import com.example.myapplication.data.model.BorrowedBookRepository
import com.example.myapplication.data.model.UserRepository
import kotlinx.coroutines.launch

class BorrowedBookViewModel(private val repository: BorrowedBookRepository,
                            private val userRepository: UserRepository,
                            private val bookDao: BookRepository
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

    private val _borrowedBooks = MutableLiveData<List<BorrowedBookDisplay>>()
    val borrowedBooks: LiveData<List<BorrowedBookDisplay>> = _borrowedBooks

    fun loadBorrowedBooks(email: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email) ?: return@launch
            val borrowedEntities = repository.getBorrowedBooksForUser(user.id) // asta e List<BorrowedBookEntity>

            val borrowedBooksDisplay = borrowedEntities.mapNotNull { borrowedEntity ->
                val book = bookDao.getBookById(borrowedEntity.bookId) ?: return@mapNotNull null

                BorrowedBookDisplay(
                    title = book.title,
                    author = book.author,
                    borrowedAt = borrowedEntity.borrowedAt  // folosești aici valoarea corectă!
                )
            }

            _borrowedBooks.postValue(borrowedBooksDisplay)
        }
    }
}
