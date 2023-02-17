package com.example.expensetracker.ui.statistics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.expensetracker.ui.*
import com.example.expensetracker.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.statistics(viewModel: StatisticsViewModel) {
    composable(Destinations.STATISTICS_ROUTE) {
        val uiState = viewModel.uiState.collectAsState()
        StatisticsScreen(uiState = uiState.value)
    }
}

fun NavController.navigateToStatistics() {
    navigate(Destinations.STATISTICS_ROUTE) {
        launchSingleTop = true
    }
}

@Composable
fun StatisticsScreen(uiState: StatisticsUiState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        SplashContent(uiState = uiState)
    }
}

@Composable
private fun SplashContent(uiState: StatisticsUiState) {
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
                modifier = Modifier.align(Alignment.Center)
            )
            else -> Content(
                uiState = uiState,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun Content(
    uiState: StatisticsUiState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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