package com.example.expensetracker.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    navigateToStatistics: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        uiState = uiState,
        addExpenseItem = viewModel::addRandomItem,
        clearAllExpenseItems = viewModel::clear,
        navigateToStatistics = navigateToStatistics
    )
}