package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.model.BookEntity
import com.example.myapplication.data.model.BookRepository
import com.example.myapplication.data.model.UserDatabase
import com.example.myapplication.viewModel.BookViewModel
import com.example.myapplication.viewModel.BookViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Toast



class BooksListFragment : Fragment(R.layout.fragment_books_list){
    private lateinit var bookViewModel: BookViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookDao = UserDatabase.getDatabase(requireContext()).bookDao()
        val repository = BookRepository(bookDao)
        val factory = BookViewModelFactory(repository)

        bookViewModel = ViewModelProvider(this, factory)[BookViewModel::class.java]

    }
}