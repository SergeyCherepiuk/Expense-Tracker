package com.example.expensetracker.ui.statistics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticsRoute(
    viewModel: StatisticsViewModel,
    navigateUp: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    StatisticsScreen(
        uiState = uiState.value,
        navigateUp = navigateUp
    )
}