package com.example.expensetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.models.Item
import com.example.expensetracker.utils.LocalDateConverter

@Database(entities = [Item::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}