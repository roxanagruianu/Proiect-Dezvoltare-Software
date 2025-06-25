package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.model.BookRepository
import com.example.myapplication.data.model.UserDatabase
import com.example.myapplication.viewModel.BookViewModel
import com.example.myapplication.viewModel.BookViewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.BookAdapter


class BooksListFragment : Fragment(R.layout.fragment_books_list){
    private lateinit var bookAdapter: BookAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: BookViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = UserDatabase.getDatabase(requireContext())
        val repository = BookRepository(db.bookDao())
        val factory = BookViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[BookViewModel::class.java]

        recyclerView = view.findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(emptyList()) { book ->
            val bundle = Bundle().apply {
                putString("title", book.title)
                putString("author", book.author)
                putInt("year", book.year)
                putString("description", book.description)
            }
            val bookDetailsFragment = BookDetailsFragment()
            bookDetailsFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, bookDetailsFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = bookAdapter

        viewModel.allBooks.observe(viewLifecycleOwner) { books ->
            bookAdapter.updateData(books)
        }
    }
}