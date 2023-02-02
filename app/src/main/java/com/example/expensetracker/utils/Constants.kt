package com.example.expensetracker.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class Constants {
    companion object {
        val emojis: List<String> = listOf(
            "\uD83D\uDE00",
            "\uD83D\uDE03",
            "\uD83D\uDE04",
            "\uD83D\uDE01",
            "\uD83D\uDE06",
            "\uD83D\uDE05",
        )

        @RequiresApi(Build.VERSION_CODES.O)
        val dates: List<LocalDate> = listOf(
            LocalDate.of(2023, 1, 25),
            LocalDate.of(2023, 1, 26),
            LocalDate.of(2023, 1, 27),
            LocalDate.of(2023, 1, 28),
            LocalDate.of(2023, 1, 29),
            LocalDate.of(2023, 1, 30),
        )
    }
}