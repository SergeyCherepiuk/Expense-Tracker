package com.example.expensetracker.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.expensetracker.ui.navigation.screens
import com.example.expensetracker.ui.theme.DarkGray
import com.example.expensetracker.ui.theme.LightGray

@Composable
fun BottomNavigationContent(navController: NavController) {
    NavigationBar(
        containerColor = LightGray,
        contentColor = DarkGray,
        tonalElevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(text = screen.label, fontSize = 14.sp) },
                selected = currentDestination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = DarkGray,
                    selectedIconColor = LightGray,
                    selectedTextColor = DarkGray,
                    unselectedIconColor = DarkGray,
                    unselectedTextColor = DarkGray
                )
            )
        }
    }
}