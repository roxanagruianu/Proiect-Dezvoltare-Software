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
import com.example.myapplication.data.model.BorrowedBookRepository
import com.example.myapplication.data.model.UserDatabase
import com.example.myapplication.data.model.UserRepository
import com.example.myapplication.viewModel.BorrowedBookViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainPageFragment : Fragment(R.layout.fragment_main_page) {
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var viewModel: BorrowedBookViewModel
    private lateinit var adapter: BookAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView)

        if (childFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mainPageFragment -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                    true
                }

                R.id.booksListFragment -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BooksListFragment())
                        .commit()
                    true
                }

                R.id.profileFragment -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }
}