package com.example.expensetracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

object Destinations {
    const val EXPENSE_DETAILS_ROUTE = "expense_details"
    const val BOTTOM_BAR_ROUTE = "bottom_bar"

    const val HOME_ROUTE = "home"
    const val STATISTICS_ROUTE = "statistics"
    const val NEW_EXPENSE_ROUTE = "new_expense"
}

sealed class Screen(
    val icon: ImageVector,
    val label: String,
    val route: String
) {
    object Home : Screen(
        icon = Icons.Default.Home,
        label = "Home",
        route = Destinations.HOME_ROUTE
    )
    object Statistics : Screen(
        icon = Icons.Default.DonutLarge,
        label = "Statistics",
        route = Destinations.STATISTICS_ROUTE
    )
}

val screens = listOf(
    Screen.Home,
    Screen.Statistics
)