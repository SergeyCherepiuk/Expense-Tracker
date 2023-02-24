package com.example.expensetracker.ui.home

import com.example.expensetracker.model.Expense
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.database.AppDatabase
import com.example.expensetracker.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
data class HomeUiState(
    val expenses: List<Expense> = listOf(),
    val isLoading: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
fun HomeUiState.monthExpenseAmount(
    month: Month = LocalDate.now().month,
    year: Int = LocalDate.now().year
): Double = expenses
    .filter { it.date.month == month && it.date.year == year }
    .sumOf { it.price }

@RequiresApi(Build.VERSION_CODES.O)
fun HomeUiState.dayExpenseAmount(
    dayOfMonth: Int = LocalDate.now().dayOfMonth,
    month: Month = LocalDate.now().month,
    year: Int = LocalDate.now().year
): Double = expenses
    .filter { it.date.dayOfMonth == dayOfMonth && it.date.month == month && it.date.year == year }
    .sumOf { it.price }

@RequiresApi(Build.VERSION_CODES.O)
fun HomeUiState.dayExpenseCount(
    dayOfMonth: Int = LocalDate.now().dayOfMonth,
    month: Month = LocalDate.now().month,
    year: Int = LocalDate.now().year
): Int = expenses
    .filter { it.date.dayOfMonth == dayOfMonth && it.date.month == month && it.date.year == year }
    .size

val HomeUiState.groupedByDate: Map<LocalDate, List<Expense>>
    get() = expenses
        .sortedByDescending { it.date }
        .groupBy { it.date }

val HomeUiState.isEmpty: Boolean get() = expenses.isEmpty()

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(context: Context) : ViewModel() {
    private val database = AppDatabase.getInstance(context)
    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(
                isLoading = true
            ) }
            _uiState.update { it.copy(
                expenses = database.expenseDao().getExpenses(),
                isLoading = false
            ) }
        }
    }

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
            _uiState.update { it.copy(
                expenses = database.expenseDao().getExpenses()
            ) }
        }
    }

    fun clear() { // for testing only
        viewModelScope.launch {
            database.expenseDao().clear()
            _uiState.update { it.copy(
                expenses = database.expenseDao().getExpenses()
            ) }
        }
    }
}