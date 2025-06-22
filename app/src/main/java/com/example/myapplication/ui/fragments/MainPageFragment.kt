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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = view?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, MainPageFragment())
                        .commit()
                    true
                }

                R.id.books -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, BooksListFragment())
                        .commit()
                    true
                }

                R.id.profile -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, ProfileFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }

    }

    //findNavController().navigate(R.id.mainPageFragment)


}