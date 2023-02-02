package com.example.expensetracker.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.models.Item
import com.example.expensetracker.ui.items.ExpenseItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ItemsList(groupedItems: Map<LocalDate, List<Item>>, sheetState: BottomSheetState) {
    val listState = rememberLazyListState()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        groupedItems.forEach { (date, items) ->
            stickyHeader {
                ItemsListDate(date)
            }
            items(items.size) {index ->
                ExpenseItem(items[index])
            }
        }
    }

    LaunchedEffect(sheetState) {
        snapshotFlow { sheetState.isCollapsed }.collect { isCollapsed ->
            if (isCollapsed) {
                listState.animateScrollToItem(index = 0)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemsListDate(date: LocalDate) {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)

    ) {
        Text(
            text = date.format(formatter),
            style = MaterialTheme.typography.h2,
            color = Color.Gray,
            fontSize = 18.sp
        )
    }
}