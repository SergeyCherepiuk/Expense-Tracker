package com.example.expensetracker.models

import java.time.LocalDate

data class Item(
    var emoji: String,
    var text: String,
    var category: String,
    var price: Double,
    var date: LocalDate
)