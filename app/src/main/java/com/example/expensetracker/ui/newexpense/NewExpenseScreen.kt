package com.example.expensetracker.ui.newexpense

import androidx.compose.foundation.layout.Box
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.expensetracker.ui.Destinations

fun NavGraphBuilder.newExpense() {
    composable(Destinations.NEW_EXPENSE_ROUTE) {
        Box { }
    }
}

fun NavController.navigateToNewExpense() {
    navigate(Destinations.NEW_EXPENSE_ROUTE) {
        launchSingleTop = true
    }
}