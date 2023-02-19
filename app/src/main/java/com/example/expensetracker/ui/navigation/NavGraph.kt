package com.example.expensetracker.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.example.expensetracker.ui.home.expenseDetails
import com.example.expensetracker.ui.home.home
import com.example.expensetracker.ui.newexpense.newExpense
import com.example.expensetracker.ui.statistics.statistics

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.BOTTOM_BAR_ROUTE
    ) {
        navigation(
            route = Destinations.BOTTOM_BAR_ROUTE,
            startDestination = Destinations.HOME_ROUTE
        ) {
            home(navController = navController)
            statistics(navController = navController)
            newExpense(navController = navController)
        }
        expenseDetails()
    }
}