package com.example.expensetracker.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.database.AppDatabase
import com.example.expensetracker.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ExpenseDetailsUiState(
    val expense: Expense? = null,
    val isLoading: Boolean = false,
)

class ExpenseDetailsViewModel(context: Context) : ViewModel() {
    private val database = AppDatabase.getInstance(context)
    private val _uiState = MutableStateFlow(ExpenseDetailsUiState())
    val uiState: StateFlow<ExpenseDetailsUiState> = _uiState.asStateFlow()

    fun getExpenseById(expenseId: Int?) {
        expenseId?.let { id ->
            viewModelScope.launch {
                _uiState.update {
                    it.copy(isLoading = true)
                }
                _uiState.update {
                    it.copy(expense = database.expenseDao().getExpenseById(id), isLoading = false)
                }
            }
        }
    }
}