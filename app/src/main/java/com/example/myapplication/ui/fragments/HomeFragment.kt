package com.example.myapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.BookAdapter
import com.example.myapplication.data.model.BookRepository
import com.example.myapplication.data.model.BorrowedBookAdapter
import com.example.myapplication.data.model.BorrowedBookRepository
import com.example.myapplication.data.model.UserDatabase
import com.example.myapplication.data.model.UserRepository
import com.example.myapplication.viewModel.BorrowedBookViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Locale


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var viewModel: BorrowedBookViewModel
    private lateinit var adapter: BorrowedBookAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewBorrowed)
        adapter = BorrowedBookAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val db = UserDatabase.getDatabase(requireContext())
        val repository = BorrowedBookRepository(db.borrowedBookDao())
        val userRepository = UserRepository(db.userDao())
        val bookRepository = BookRepository(db.bookDao())
        viewModel = BorrowedBookViewModel(repository, userRepository, bookRepository)

        val borrowedBookId = 4 // sau ID-ul real

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val date = sdf.parse("01/06/2025 12:00")
        val fixedTime = date?.time ?: System.currentTimeMillis()

        val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val email = prefs.getString("logged_in_email", null)

        if (email != null) {
            viewModel.loadBorrowedBooks(email)

            viewModel.borrowedBooks.observe(viewLifecycleOwner) { books ->
                adapter.updateData(books)
            }
        }
    }
}