package com.example.expensetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.items
import com.example.expensetracker.models.Item
import com.example.expensetracker.ui.items.ExpenseItem
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ItemsList(items: List<Item>, sheetState: BottomSheetState) {
    val groupedItems = items
        .sortedBy { item -> item.date }
        .reversed()
        .groupBy { item -> item.date }

    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
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

    LaunchedEffect(sheetState) {
        snapshotFlow { sheetState.isCollapsed }.collect { isCollapsed ->
            if (isCollapsed) {
                listState.animateScrollToItem(index = 0)
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
            .height(60.dp)
            .background(Color.White)

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h2,
            color = Color.Gray,
            fontSize = 18.sp
        )
    }
}