package com.example.expensetracker.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.example.expensetracker.ui.home.ExpenseDetailsViewModel
import com.example.expensetracker.ui.home.HomeViewModel
import com.example.expensetracker.ui.home.expenseDetails
import com.example.expensetracker.ui.home.home
import com.example.expensetracker.ui.newexpense.newExpense
import com.example.expensetracker.ui.statistics.StatisticsViewModel
import com.example.expensetracker.ui.statistics.statistics

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    val homeViewModel = HomeViewModel(LocalContext.current)
    val statisticsViewModel = StatisticsViewModel(LocalContext.current)
    val expenseDetailsViewModel = ExpenseDetailsViewModel(LocalContext.current)

    NavHost(
        navController = navController,
        startDestination = Destinations.BOTTOM_BAR_ROUTE
    ) {
        navigation(
            route = Destinations.BOTTOM_BAR_ROUTE,
            startDestination = Destinations.HOME_ROUTE
        ) {
            home(
                navController = navController,
                viewModel = homeViewModel
            )
            statistics(
                navController = navController,
                viewModel = statisticsViewModel
            )
        }
        newExpense(navController = navController)
        expenseDetails(viewModel = expenseDetailsViewModel)
    }
}