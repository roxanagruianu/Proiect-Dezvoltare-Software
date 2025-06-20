package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.myapplication.R

class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.textViewLogin)
        val fullText = "Already have an account? Click here"
        val spannable = SpannableString(fullText)

        val start = fullText.indexOf("Click")
        val end = fullText.length

        // Sublinează și face clickabil textul
        spannable.setSpan(UnderlineSpan(), start, end, 0)
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                view.findNavController().navigate(R.id.loginFragment)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannable
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}
