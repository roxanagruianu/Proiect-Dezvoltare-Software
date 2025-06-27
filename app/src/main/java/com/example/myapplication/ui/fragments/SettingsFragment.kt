package com.example.myapplication.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.model.UserDatabase
import com.example.myapplication.data.model.UserRepository
import com.example.myapplication.viewModel.UserViewModel
import com.example.myapplication.viewModel.UserViewModelFactory

class SettingsFragment : Fragment(R.layout.fragment_settings){
    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton = view.findViewById<Button>(R.id.buttonLogOut)
        logoutButton.setOnClickListener {
            val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            prefs.edit().remove("logged_in_email").apply()

            findNavController().navigate(R.id.loginFragment)
        }

        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
        val deleteButton = view.findViewById<Button>(R.id.buttonDelete)
        deleteButton.setOnClickListener {
            val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val email = prefs.getString("logged_in_email", null)

            if (email != null) {
                findNavController().navigate(R.id.loginFragment)

                viewModel.deleteAccount(email)

                prefs.edit().clear().apply()


            }
        }
    }
}