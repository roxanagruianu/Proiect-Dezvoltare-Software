package com.example.myapplication.data.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class BookAdapter(private var books: List<BookEntity>,
                  private val onItemClick: (BookEntity) -> Unit) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)

        init {
            itemView.setOnClickListener {
                onItemClick(books[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.textViewTitle.text = book.title
        holder.textViewAuthor.text = book.author
    }

    override fun getItemCount(): Int = books.size

    fun updateData(newBooks: List<BookEntity>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
