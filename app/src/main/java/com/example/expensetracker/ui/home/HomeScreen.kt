package com.example.expensetracker.ui.home

import com.example.expensetracker.model.Expense
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.ui.theme.*
import com.example.expensetracker.utils.currentFraction
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.sqrt

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    uiState: HomeUiState
) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
        animationSpec = tween(
            durationMillis = 500,
            easing = CubicBezierEasing(a = 0.1f, b = 0.75f, c = 0.3f, d = 1f)
        )
    )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

    val configuration = LocalConfiguration.current
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = configuration.screenHeightDp.dp * 0.7f,
        sheetShape = RoundedCornerShape(
            topStart = 35.dp * sqrt(scaffoldState.currentFraction),
            topEnd = 35.dp * sqrt(scaffoldState.currentFraction)
        ),
        sheetContent = {
            BottomSheetContent(
                sheetState = sheetState,
                uiState = uiState,
            )
        }
    ) {
        Header(
            viewModel = viewModel,
            scaffoldState = scaffoldState,
            uiState = uiState
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun BottomSheetContent(
    sheetState: BottomSheetState,
    uiState: HomeUiState
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray)
            .padding(horizontal = 20.dp)
    ) {
        if (uiState.isEmpty()) {
            // TODO: Image for no expenses
        } else {
            ItemsList(
                sheetState = sheetState,
                uiState = uiState
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ItemsList(
    sheetState: BottomSheetState,
    uiState: HomeUiState
) {
    val listState = rememberLazyListState()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        val groupedExpenses: Map<LocalDate, List<Expense>> = uiState.groupByDate()
        item {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
        }
        item {
            groupedExpenses.forEach { (date, expenses) ->
                DayItem(
                    date = date,
                    expenses = expenses
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
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
fun DayItem(
    date: LocalDate,
    expenses: List<Expense>
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .background(LightYellow)
    ) {
        val formatter = DateTimeFormatter.ofPattern("MMMM dd")
        val dateFormatted = when (date) {
            LocalDate.now() -> "Today"
            LocalDate.now().minusDays(1) -> "Yesterday"
            else -> date.format(formatter)
        }
        Text(
            text = dateFormatted,
            fontSize = 20.sp,
            fontFamily = SourceSans3,
            fontWeight = FontWeight.Bold,
            color = DarkGray,
            modifier = Modifier
                .padding(start = 20.dp, top = 15.dp, bottom = 5.dp)
        )
        Column {
            expenses.forEachIndexed { index, expense ->
                ExpenseItem(expense = expense)
                if (index != expenses.size-1) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(LightGray)
                    )
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(
    expense: Expense
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightYellow)
            .padding(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(White)
        ) {
            Text(
                text = expense.emoji,
                fontSize = 20.sp,
                fontFamily = NotoEmoji,
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )
        }
        Spacer(
            modifier = Modifier
                .width(10.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = expense.title,
                fontSize = 20.sp,
                fontFamily = SourceSans3,
                fontWeight = FontWeight.SemiBold,
                color = DarkGray
            )
            Text(
                text = expense.category,
                fontSize = 14.sp,
                fontFamily = SourceSans3,
                fontWeight = FontWeight.Normal,
                color = Gray
            )
        }
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Yellow)
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = String.format("$%.2f", expense.price),
                fontSize = 16.sp,
                fontFamily = SourceSans3,
                fontWeight = FontWeight.SemiBold,
                color = DarkGray
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Header(
    viewModel: HomeViewModel,
    scaffoldState: BottomSheetScaffoldState,
    uiState: HomeUiState
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .offset(y = -(1f - scaffoldState.currentFraction).dp * 100)
                .alpha(scaffoldState.currentFraction)
                .scale(scaffoldState.currentFraction / 2 + 0.5f)
        ) {
            Text(
                text = "Spent this month",
                fontSize = 16.sp,
                fontFamily = SourceSans3,
                fontWeight = FontWeight.Bold,
                color = LightYellow,
                modifier = Modifier.clickable {
                    viewModel.clear()
                }
            )
            Text(
                text = String.format("$%.2f", uiState.getTotal()),
                fontSize = 58.sp,
                fontFamily = SourceSans3,
                fontWeight = FontWeight.Bold,
                color = LightYellow,
                modifier = Modifier.clickable {
                    viewModel.addRandomItem()
                }
            )
        }
    }
}