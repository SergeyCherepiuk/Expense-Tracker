package com.example.expensetracker.ui.statistics

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.database.AppDatabase
import com.example.expensetracker.ui.ExpensesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class StatisticsViewModel(context: Context) : ViewModel() {
    private val database = AppDatabase.getInstance(context)
    private val _uiState = MutableStateFlow(ExpensesUiState(isLoading = true))
    val uiState: StateFlow<ExpensesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(expenses = database.expenseDao().getExpenses(), isLoading = false)
            }
        }
    }
}