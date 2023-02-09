package com.example.expensetracker.database

import com.example.expensetracker.model.Expense
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.utils.LocalDateConverter

@Database(entities = [Expense::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}