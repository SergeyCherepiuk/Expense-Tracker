package com.example.expensetracker.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.expensetracker.model.Expense
import java.time.LocalDate
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
data class ExpensesUiState(
    val expenses: List<Expense> = listOf(),
    val isLoading: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
fun ExpensesUiState.monthExpenseAmount(
    month: Month = LocalDate.now().month,
    year: Int = LocalDate.now().year
): Double = expenses
    .filter { it.date.month == month && it.date.year == year }
    .sumOf { it.price }

@RequiresApi(Build.VERSION_CODES.O)
fun ExpensesUiState.dayExpenseAmount(
    dayOfMonth: Int = LocalDate.now().dayOfMonth,
    month: Month = LocalDate.now().month,
    year: Int = LocalDate.now().year
): Double = expenses
    .filter { it.date.dayOfMonth == dayOfMonth && it.date.month == month && it.date.year == year }
    .sumOf { it.price }

@RequiresApi(Build.VERSION_CODES.O)
fun ExpensesUiState.dayExpenseCount(
    dayOfMonth: Int = LocalDate.now().dayOfMonth,
    month: Month = LocalDate.now().month,
    year: Int = LocalDate.now().year
): Int = expenses
    .filter { it.date.dayOfMonth == dayOfMonth && it.date.month == month && it.date.year == year }
    .size

val ExpensesUiState.totalExpensesAmount: Double get() = expenses.sumOf { it.price }

val ExpensesUiState.groupedByDate: Map<LocalDate, List<Expense>>
    get() = expenses
        .sortedByDescending { it.date }
        .groupBy { it.date }

val ExpensesUiState.isEmpty: Boolean get() = expenses.isEmpty()