package com.example.expensetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.models.Item
import com.example.expensetracker.ui.items.ExpenseItem
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemsList(items: List<Item>) {
    val groupedItems = items
        .sortedBy { item -> item.date }
        .reversed()
        .groupBy { item -> item.date }

    LazyColumn {
        groupedItems.forEach { (date, items) ->
            stickyHeader {
                val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                ItemsListDate(date.format(formatter))
            }
            items(items.size) {index ->
                ExpenseItem(items[index])
            }
        }
    }
}

@Composable
fun ItemsListDate(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(15.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = Color.Gray,
            fontSize = 18.sp
        )
    }
}