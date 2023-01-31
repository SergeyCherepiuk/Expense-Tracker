package com.example.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.expensetracker.models.Item
import com.example.expensetracker.ui.items.ExpenseItem
import com.example.expensetracker.ui.screens.ItemsListScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import java.time.LocalDate
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
val dates: List<LocalDate> = listOf(
    LocalDate.of(2023, 1, 28),
    LocalDate.of(2023, 1, 27),
    LocalDate.of(2023, 1, 26),
    LocalDate.of(2023, 1, 25),
)

@RequiresApi(Build.VERSION_CODES.O)
val items: List<Item> = List(30) { index ->
    Item(
        emoji = "\uD83E\uDD21",
        text = "Expense info #$index",
        category = "Category #$index",
        price = Random.nextDouble(from = 0.01, until = 500.0),
        date = dates.random()
    )
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                ItemsListScreen(items = items)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ItemsListScreen(items = items)
}