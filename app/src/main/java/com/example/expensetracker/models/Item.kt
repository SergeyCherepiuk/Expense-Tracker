package com.example.expensetracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.expensetracker.utils.LocalDateConverter
import java.time.LocalDate

@Entity(tableName = "expense_item_table")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int?,

    @ColumnInfo var emoji: String,

    @ColumnInfo var text: String,

    @ColumnInfo var category: String,

    @ColumnInfo var price: Double,

    @TypeConverters(LocalDateConverter::class)
    @ColumnInfo var date: LocalDate
)