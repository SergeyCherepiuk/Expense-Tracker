package com.example.expensetracker.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.ui.home.HomeViewModel
import com.example.expensetracker.ui.home.home
import com.example.expensetracker.ui.newexpense.newExpense
import com.example.expensetracker.ui.statistics.StatisticsViewModel
import com.example.expensetracker.ui.statistics.statistics

object Destinations {
    const val HOME_ROUTE = "home"
    const val EXPENSE_DETAILS_ROUTE = "expense_details"
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
    object NewExpense : Screen(
        icon = Icons.Default.Add,
        label = "New expense",
        route = Destinations.NEW_EXPENSE_ROUTE
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.HOME_ROUTE
) {
    val homeViewModel = HomeViewModel(LocalContext.current)
    val statisticsViewModel = StatisticsViewModel(LocalContext.current)

    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            home(viewModel = homeViewModel)
            statistics(viewModel = statisticsViewModel)
            newExpense()
        }
    }
}