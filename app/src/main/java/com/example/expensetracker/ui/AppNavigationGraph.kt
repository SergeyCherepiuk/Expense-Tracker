package com.example.expensetracker.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.ui.home.HomeRoute
import com.example.expensetracker.ui.home.HomeViewModel
import com.example.expensetracker.ui.statistics.StatisticsRoute
import com.example.expensetracker.ui.statistics.StatisticsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    navigationActions: NavigationActions,
    startDestination: String = Destinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.HOME_ROUTE) {
            val homeViewModel = HomeViewModel(LocalContext.current)
            HomeRoute(
                viewModel = homeViewModel,
                navigateToStatistics = navigationActions.navigateToStatistics
            )
        }
        composable(Destinations.STATISTICS_ROUTE) {
            val statisticsViewModel = StatisticsViewModel(LocalContext.current)
            StatisticsRoute(
                viewModel = statisticsViewModel,
                navigateUp = navigationActions.navigateUp
            )
        }
    }
}