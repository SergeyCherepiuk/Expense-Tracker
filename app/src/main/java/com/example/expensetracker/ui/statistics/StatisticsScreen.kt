package com.example.expensetracker.ui.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.ui.ExpensesUiState
import com.example.expensetracker.ui.theme.*
import com.example.expensetracker.ui.totalExpensesAmount

@Composable
fun StatisticsScreen(
    uiState: ExpensesUiState,
    navigateUp: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        SplashContent(
            uiState = uiState,
            navigateUp = navigateUp
        )
    }
}

@Composable
private fun SplashContent(
    uiState: ExpensesUiState,
    navigateUp: () -> Unit
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(horizontal = 20.dp)
    ) {
        when {
            uiState.isLoading -> CircularProgressIndicator(
                color = DarkGray,
                strokeWidth = 4.dp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            else -> Content(
                uiState = uiState,
                navigateUp = navigateUp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun Content(
    uiState: ExpensesUiState,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                    navigateUp()
                }
        ) {
            Text(
                text = "Total spent",
                fontSize = 24.sp,
                fontFamily = SourceSans3,
                fontWeight = FontWeight.Bold,
                color = Yellow
            )
            Text(
                text = String.format("$%.2f", uiState.totalExpensesAmount),
                fontFamily = SourceSans3,
                fontWeight = FontWeight.Bold,
                fontSize = 64.sp,
                color = Yellow
            )
        }
    }
}