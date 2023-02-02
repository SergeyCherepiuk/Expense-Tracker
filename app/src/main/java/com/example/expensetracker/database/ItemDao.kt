package com.example.expensetracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expensetracker.models.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM expense_item_table")
    suspend fun getItems(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Query("DELETE FROM expense_item_table")
    suspend fun clear()
}