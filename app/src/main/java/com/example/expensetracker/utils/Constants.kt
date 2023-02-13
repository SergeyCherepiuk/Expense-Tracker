package com.example.expensetracker.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class Constants {
    companion object {
        val emojis: List<String> = listOf(
            "\uD83C\uDF59",
            "\uD83E\uDD58",
            "\uD83E\uDD91",
            "\uD83D\uDEB4\uD83C\uDFFE\u200D♀️",
            "\uD83D\uDCAA\uD83C\uDFFB",
            "\uD83D\uDC80",
        )

        @RequiresApi(Build.VERSION_CODES.O)
        val dates: List<LocalDate> = List(45) { index ->
            LocalDate.now().minusDays(index.toLong())
        }
    }
}