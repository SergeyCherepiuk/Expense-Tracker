package com.example.expensetracker.ui.statistics

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.database.AppDatabase
import com.example.expensetracker.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
data class StatisticsUiState(
    val expenses: List<Expense> = listOf(),
    val isLoading: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
fun StatisticsUiState.monthExpenseAmount(
    month: Month = LocalDate.now().month,
    year: Int = LocalDate.now().year
): Double = expenses
    .filter { it.date.month == month && it.date.year == year }
    .sumOf { it.price }

val StatisticsUiState.totalExpensesAmount: Double get() = expenses.sumOf { it.price }

val StatisticsUiState.isEmpty: Boolean get() = expenses.isEmpty()

@RequiresApi(Build.VERSION_CODES.O)
class StatisticsViewModel(context: Context) : ViewModel() {
    private val database = AppDatabase.getInstance(context)
    private val _uiState = MutableStateFlow(StatisticsUiState(isLoading = true))
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(expenses = database.expenseDao().getExpenses(), isLoading = false)
            }
        }
    }
}