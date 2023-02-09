package com.example.expensetracker.database

import com.example.expensetracker.model.Expense
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses")
    suspend fun getExpenses(): List<Expense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Expense)

    @Query("DELETE FROM expenses")
    suspend fun clear()
}