package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.model.UserDatabase
import com.example.myapplication.data.model.UserEntity
import com.example.myapplication.data.model.UserRepository
import com.example.myapplication.viewModel.UserViewModel
import com.example.myapplication.viewModel.UserViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.textViewLogin)
        val fullText = "Already have an account? Click here"
        val spannable = SpannableString(fullText)

        val start = fullText.indexOf("Click")
        val end = fullText.length

        spannable.setSpan(UnderlineSpan(), start, end, 0)
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                view.findNavController().navigate(R.id.loginFragment)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannable
        textView.movementMethod = LinkMovementMethod.getInstance()

        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        val firstNameInput = view.findViewById<EditText>(R.id.editTextRegisterFirstName)
        val lastNameInput = view.findViewById<EditText>(R.id.editTextRegisterLastName)
        val emailInput = view.findViewById<EditText>(R.id.editTextRegisterEmailAddress)
        val passwordInput = view.findViewById<EditText>(R.id.editTextRegisterPassword)
        val passwordInput2 = view.findViewById<EditText>(R.id.editTextTextPassword2)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val firstName = firstNameInput.text.toString()
            val lastName = lastNameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "Toate câmpurile sunt obligatorii", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Adresa de email nu este validă", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val password1 = passwordInput.text.toString().trim()
            val password2 = passwordInput2.text.toString().trim()

            if (password1 != password2) {
                Toast.makeText(requireContext(), "Parolele nu coincid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = UserEntity(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password
            )

            viewModel.registerUser(user)

            Toast.makeText(requireContext(), "Înregistrare reușită!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.mainPageFragment)
        }

    }
}
