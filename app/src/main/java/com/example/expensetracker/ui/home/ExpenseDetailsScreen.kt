package com.example.expensetracker.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensetracker.model.Expense
import com.example.expensetracker.ui.navigation.Destinations
import com.example.expensetracker.ui.theme.*
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.expenseDetails(viewModel: ExpenseDetailsViewModel) {
    composable(
        Destinations.EXPENSE_DETAILS_ROUTE.plus("/{id}"),
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { entry ->
        val uiState by viewModel.uiState.collectAsState()
        val id = entry.arguments?.getInt("id")
        viewModel.getExpenseById(id)
        ExpenseDetailsScreen(uiState)
    }
}

fun NavController.navigateToExpenseDetails(id: Int) {
    navigate(Destinations.EXPENSE_DETAILS_ROUTE.plus("/$id")) {
        launchSingleTop = true
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseDetailsScreen(uiState: ExpenseDetailsUiState) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray)
            .padding(horizontal = 20.dp)
    ) {
        when (uiState.expense) {
            null -> androidx.compose.material.CircularProgressIndicator(
                color = DarkGray,
                strokeWidth = 4.dp,
                modifier = Modifier.align(Alignment.Center)
            )
            else -> Content(uiState = uiState)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Content(uiState: ExpenseDetailsUiState) {
    val expense: Expense = uiState.expense!!
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(White)
                .padding(25.dp)
        ) {
            Text(
                text = expense.emoji,
                fontSize = 64.sp,
                fontFamily = NotoEmoji,
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = expense.title,
            fontSize = 32.sp,
            fontFamily = SourceSans3,
            fontWeight = FontWeight.SemiBold,
            color = DarkGray
        )
        Text(
            text = expense.category,
            fontSize = 24.sp,
            fontFamily = SourceSans3,
            fontWeight = FontWeight.Medium,
            color = Gray
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = String.format("$%.2f", expense.price),
            fontSize = 24.sp,
            fontFamily = SourceSans3,
            fontWeight = FontWeight.Medium,
            color = DarkGray
        )
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        Text(
            text = expense.date.format(formatter),
            fontSize = 24.sp,
            fontFamily = SourceSans3,
            fontWeight = FontWeight.Medium,
            color = DarkGray
        )
    }
}