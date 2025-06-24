package com.example.myapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.model.UserDatabase
import com.example.myapplication.data.model.UserEntity
import com.example.myapplication.data.model.UserRepository
import com.example.myapplication.viewModel.UserViewModel
import com.example.myapplication.viewModel.UserViewModelFactory
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.textViewRegister)
        val fullText = "New user? Click here"
        val spannable = SpannableString(fullText)

        val start = fullText.indexOf("Click")
        val end = fullText.length

        spannable.setSpan(UnderlineSpan(), start, end, 0)
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                view.findNavController().navigate(R.id.registerFragment)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannable
        textView.movementMethod = LinkMovementMethod.getInstance()

        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        val emailInput = view.findViewById<EditText>(R.id.editTextLoginEmailAddress)
        val passwordInput = view.findViewById<EditText>(R.id.editTextLoginPassword)
        val loginButton = view.findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "Email și parolă necesare", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(email, password)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginEvent.collectLatest { user ->
                if (user != null) {
                    val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    prefs.edit()
                        .putString("logged_in_email", user.email)
                        .apply()
                    Toast.makeText(requireContext(), "Bine ai venit, ${user.firstName}", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.mainPageFragment)
                } else {
                    Toast.makeText(requireContext(), "Date incorecte", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}