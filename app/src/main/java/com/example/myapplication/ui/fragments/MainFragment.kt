package com.example.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R

class MainFragment : Fragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val loginBtn = view.findViewById<Button>(R.id.btn_login)
        val registerBtn = view.findViewById<Button>(R.id.btn_register)

        loginBtn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        registerBtn.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }

    }
}