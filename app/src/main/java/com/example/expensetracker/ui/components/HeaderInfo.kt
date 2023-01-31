package com.example.expensetracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderInfo(balance: Double, currentFraction: Float) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 125.dp)
    ) {
        Text(
            text = "Spent this month",
            style = MaterialTheme.typography.h2,
            fontSize = (18 - (1f - currentFraction) * 10).sp,
            color = Color.Gray
        )
        Row {
            Text(
                text = "$",
                style = MaterialTheme.typography.h1,
                fontSize = (48 - (1f - currentFraction) * 30).sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 9.dp)
            )
            Text(
                text = String.format("%d", balance.toInt()),
                style = MaterialTheme.typography.h3,
                fontSize = (72 - (1f - currentFraction) * 30).sp,
                color = Color.Black
            )
            Text(
                text = String.format("%.2f", balance % 1).substringAfter('0'),
                style = MaterialTheme.typography.h1,
                fontSize = (48 - (1f - currentFraction) * 30).sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 9.dp)
            )
        }
    }
}