package com.example.expensetracker.ui.items

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.models.Item

@Composable
fun ExpenseItem(item: Item) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
        ) {
            Text(
                text = item.emoji,
                style = MaterialTheme.typography.body1,
                fontSize = 28.sp
            )
        }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(2f)
                .padding(5.dp)
        ) {
            Text(
                text = item.text,
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                fontSize = 20.sp
            )
            Text(
                text = item.category,
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                fontSize = 14.sp

            )
        }
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        ) {
            Text(
                text = String.format("$%.2f", item.price),
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }
}