package com.example.myapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.model.UserDatabase
import com.example.myapplication.data.model.UserRepository
import com.example.myapplication.databinding.FragmentPersonalDataBinding
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.viewModel.UserViewModel
import com.example.myapplication.viewModel.UserViewModelFactory

class PersonalDataFragment : Fragment(R.layout.fragment_personal_data) {
    private var _binding: FragmentPersonalDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = UserDatabase.getDatabase(requireContext())
        val userDao = db.userDao()
        val repository = UserRepository(userDao)
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory)[UserViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val email = prefs.getString("logged_in_email", null)

        if (email != null) {
            viewModel.loadUserByEmail(email)
        }

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.textViewFirstName.text = it.firstName
                binding.textViewLastName.text = it.lastName
                binding.textViewEmail.text = it.email
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}