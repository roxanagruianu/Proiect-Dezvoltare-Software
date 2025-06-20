package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.UnderlineSpan
import androidx.navigation.findNavController
import com.example.myapplication.R

class LoginFragment : Fragment(R.layout.fragment_login) {
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
    }
}