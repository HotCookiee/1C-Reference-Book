package com.example.a1c_reference_book.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {

    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))

    private val dateTimeFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale("ru"))

    fun formatDate(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
    }

    fun formatDateTime(timestamp: Long): String {
        return dateTimeFormat.format(Date(timestamp))
    }

    fun formatRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < 60_000 -> "только что"
            diff < 3600_000 -> "${diff / 60_000} мин назад"
            diff < 86400_000 -> "${diff / 3600_000} ч назад"
            diff < 172800_000 -> "вчера"
            else -> formatDate(timestamp)
        }
    }
}
