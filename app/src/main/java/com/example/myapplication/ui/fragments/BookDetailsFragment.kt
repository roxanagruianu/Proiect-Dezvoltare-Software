package com.example.myapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.data.model.BorrowedBookEntity
import com.example.myapplication.data.model.UserDatabase
import kotlinx.coroutines.launch

class BookDetailsFragment : Fragment(R.layout.fragment_book_details) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("title")
        val author = arguments?.getString("author")
        val year = arguments?.getInt("year")
        val description = arguments?.getString("description")

        view.findViewById<TextView>(R.id.textTitle).text = title
        view.findViewById<TextView>(R.id.textAuthor).text = author
        view.findViewById<TextView>(R.id.textYear).text = "Published in " + year?.toString() ?: ""
        view.findViewById<TextView>(R.id.textDescription).text = description
        view.findViewById<TextView>(R.id.textDescription).setMovementMethod(ScrollingMovementMethod())

        val buttonBorrow = view.findViewById<Button>(R.id.buttonBorrow)
        buttonBorrow.setOnClickListener {
            val title = arguments?.getString("title")
            val author = arguments?.getString("author")

            val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val email = prefs.getString("logged_in_email", null)

            if (email != null && title != null && author != null) {
                val db = UserDatabase.getDatabase(requireContext())
                val userDao = db.userDao()
                val bookDao = db.bookDao()
                val borrowedBookDao = db.borrowedBookDao()

                lifecycleScope.launch {
                    val user = userDao.getUserByEmail(email)
                    val book = bookDao.getBookByTitleAndAuthor(title, author)

                    if (user != null && book != null) {
                        val borrowedBook = BorrowedBookEntity(
                            userId = user.id,
                            bookId = book.id,
                            borrowedAt = System.currentTimeMillis()
                        )

                        borrowedBookDao.insertBorrowedBook(borrowedBook)

                        Toast.makeText(requireContext(), "Cartea a fost împrumutată!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Eroare: utilizatorul sau cartea nu a fost găsită.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Datele nu sunt complete pentru împrumut.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}