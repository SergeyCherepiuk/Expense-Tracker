package com.example.expensetracker.ui.newexpense

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.expensetracker.ui.components.BottomNavigationContent
import com.example.expensetracker.ui.navigation.Destinations

fun NavGraphBuilder.newExpense(navController: NavController) {
    composable(Destinations.NEW_EXPENSE_ROUTE) {
        Scaffold(
            bottomBar = { BottomNavigationContent(navController = navController) }
        ) {
            Box(modifier = Modifier.padding(it)) {

            }
        }
    }
}

fun NavController.navigateToNewExpense() {
    navigate(Destinations.NEW_EXPENSE_ROUTE) {
        launchSingleTop = true
    }
}