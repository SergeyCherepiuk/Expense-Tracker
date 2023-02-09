package com.example.expensetracker.ui.home

import com.example.expensetracker.model.Expense
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.expensetracker.database.Database
import com.example.expensetracker.utils.Constants
import com.example.expensetracker.utils.LocalDateConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

data class HomeUiState(
    val expenses: List<Expense> = listOf(),
)

fun HomeUiState.getTotal(): Double = expenses.sumOf { it.price }

fun HomeUiState.groupByDate(): Map<LocalDate, List<Expense>> {
    return expenses
        .sortedByDescending { it.date }
        .groupBy { it.date }
}

fun HomeUiState.isEmpty(): Boolean = expenses.isEmpty()

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val database = Room.databaseBuilder(
        context = application.applicationContext,
        klass = Database::class.java,
        name = "database"
    ).addTypeConverter(LocalDateConverter()).build()

    // TODO: Don't use getAllExpenses from ExpensesDao for some reasons
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addRandomItem() { // for testing only
        val expense = Expense(
            emoji = Constants.emojis[Random.nextInt(until = Constants.emojis.size-1)],
            title = "Text",
            category = "Category",
            price = Random.nextDouble(from = 0.01, until = 500.0),
            date = Constants.dates[Random.nextInt(until = Constants.dates.size-1)]
        )
        viewModelScope.launch {
            database.expenseDao().insert(expense)
            _uiState.update {
                val expenses: MutableList<Expense> = _uiState.value.expenses.toMutableList()
                expenses.add(expense)
                it.copy(expenses = expenses)
            }
        }
    }

    fun clear() { // for testing only
        viewModelScope.launch {
            database.expenseDao().clear()
            _uiState.update {
                val expenses: MutableList<Expense> = mutableListOf()
                it.copy(expenses = expenses)
            }
        }
    }
}