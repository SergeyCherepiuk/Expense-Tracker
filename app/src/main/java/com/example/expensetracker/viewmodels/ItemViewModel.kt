package com.example.expensetracker.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.expensetracker.database.Database
import com.example.expensetracker.models.Item
import com.example.expensetracker.utils.LocalDateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemViewModel(context: Context) : ViewModel() {
    private val database = Room.databaseBuilder(
        context = context,
        klass = Database::class.java,
        name = "database"
    ).addTypeConverter(LocalDateConverter()).build()

    private val _items = MutableStateFlow(value = emptyList<Item>().toMutableList())
    val items: StateFlow<List<Item>> get() = _items.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _items.value = database.itemDao().getItems().toMutableList()
        }
    }

    fun addItem(item: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            database.itemDao().insert(item)
            _items.value = (_items.value + item).toMutableList()
        }
    }

    fun clear() {
        CoroutineScope(Dispatchers.IO).launch {
            database.itemDao().clear()
            _items.value = emptyList<Item>().toMutableList()
        }
    }
}