package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.media3.common.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainPageFragment : Fragment(R.layout.fragment_main_page) {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mainPageFragment -> {
                    findNavController().navigate(R.id.mainPageFragment)
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