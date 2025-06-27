package com.example.myapplication.data.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

data class BorrowedBookDisplay(
    val title: String,
    val author: String,
    val borrowedAt: Long
) {
    val returnDate: String
        get() {
            val returnMillis = borrowedAt + TimeUnit.DAYS.toMillis(14)
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            return sdf.format(Date(returnMillis))
        }
}
