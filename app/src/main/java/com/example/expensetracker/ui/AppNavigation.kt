package com.example.expensetracker.ui

import androidx.navigation.NavController

object Destinations {
    const val HOME_ROUTE = "home"
    const val STATISTICS_ROUTE = "statistics"
}

class NavigationActions(navController: NavController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(Destinations.HOME_ROUTE) {
            launchSingleTop = true
        }
    }
    val navigateToStatistics: () -> Unit = {
        navController.navigate(Destinations.STATISTICS_ROUTE) {
            launchSingleTop = true
        }
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}