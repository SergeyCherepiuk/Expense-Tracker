package com.example.expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.expensetracker.utils.LocalDateConverter
import java.time.LocalDate

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,

    @ColumnInfo var emoji: String,

    @ColumnInfo var title: String,

    @ColumnInfo var category: String,

    @ColumnInfo var price: Double,

    @TypeConverters(LocalDateConverter::class)
    @ColumnInfo var date: LocalDate
)