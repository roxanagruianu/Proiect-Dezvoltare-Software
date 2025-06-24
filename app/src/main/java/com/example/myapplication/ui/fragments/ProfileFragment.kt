package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileFragment : Fragment(R.layout.fragment_profile){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonPersonalData = view.findViewById<Button>(R.id.buttonPersonalData)
        buttonPersonalData.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PersonalDataFragment())
                .addToBackStack(null)
                .commit()
        }

        val buttonSettings = view.findViewById<Button>(R.id.buttonSettings)
        buttonSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}