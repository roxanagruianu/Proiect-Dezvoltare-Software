package com.example.myapplication.data.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.util.concurrent.TimeUnit
import android.graphics.Color

class BorrowedBookAdapter(
    private var borrowedBooks: List<BorrowedBookDisplay>
) : RecyclerView.Adapter<BorrowedBookAdapter.BorrowedBookViewHolder>() {

    inner class BorrowedBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewBorrowedTitle)
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewBorrowedAuthor)
        val textViewReturnDate: TextView = itemView.findViewById(R.id.textViewReturnDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BorrowedBookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_borrowed_book, parent, false)
        return BorrowedBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BorrowedBookViewHolder, position: Int) {
        val book = borrowedBooks[position]
        holder.textViewTitle.text = book.title
        holder.textViewAuthor.text = book.author
        holder.textViewReturnDate.text = "Return by: ${book.returnDate}"

        val isOverdue = System.currentTimeMillis() > (book.borrowedAt + TimeUnit.DAYS.toMillis(14))

        if (isOverdue) {
            holder.itemView.setBackgroundResource(R.drawable.gradient_file)
        }
    }

    override fun getItemCount(): Int = borrowedBooks.size

    fun updateData(newBooks: List<BorrowedBookDisplay>) {
        borrowedBooks = newBooks
        notifyDataSetChanged()
    }
}
