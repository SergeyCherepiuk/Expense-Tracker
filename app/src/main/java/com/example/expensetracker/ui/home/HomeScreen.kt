package com.example.expensetracker.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.expensetracker.R
import com.example.expensetracker.model.Expense
import com.example.expensetracker.ui.*
import com.example.expensetracker.ui.components.BottomNavigationContent
import com.example.expensetracker.ui.navigation.Destinations
import com.example.expensetracker.ui.theme.*
import com.example.expensetracker.utils.currentFraction
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.sqrt

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.home(
    viewModel: HomeViewModel,
    navController: NavController
) {
    composable(Destinations.HOME_ROUTE) {
        val uiState by viewModel.uiState.collectAsState()
        Scaffold(
            bottomBar = { BottomNavigationContent(navController = navController) }
        ) {
            Box(modifier = Modifier.padding(it)) {
                HomeScreen(
                    uiState = uiState,
                    addExpenseItem = { viewModel.addRandomItem() },
                    clearAllExpenseItems = { viewModel.clear() },
                    navigateToExpenseDetails = { id -> navController.navigateToExpenseDetails(id) }
                )
            }
        }
    }
}

fun NavController.navigateToHome() {
    navigate(Destinations.HOME_ROUTE) {
        launchSingleTop = true
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    addExpenseItem: () -> Unit,
    clearAllExpenseItems: () -> Unit,
    navigateToExpenseDetails: (Int) -> Unit
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
        sheetPeekHeight = configuration.screenHeightDp.dp * 0.65f,
        sheetShape = RoundedCornerShape(
            topStart = 35.dp * sqrt(scaffoldState.currentFraction),
            topEnd = 35.dp * sqrt(scaffoldState.currentFraction)
        ),
        sheetContent = {
            BottomSheetContent(
                sheetState = sheetState,
                uiState = uiState,
                navigateToExpenseDetails = navigateToExpenseDetails
            )
        }
    ) {
        Header(
            scaffoldState = scaffoldState,
            uiState = uiState,
            addExpenseItem = addExpenseItem,
            clearAllExpenseItems = clearAllExpenseItems
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun BottomSheetContent(
    sheetState: BottomSheetState,
    uiState: HomeUiState,
    navigateToExpenseDetails: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray)
            .padding(horizontal = 20.dp)
    ) {
        when {
            uiState.isLoading -> CircularProgressIndicator(
                color = DarkGray,
                strokeWidth = 4.dp,
                modifier = Modifier.align(Alignment.Center)
            )
            uiState.isEmpty -> EmptyBottomSheet()
            else -> ItemsList(
                sheetState = sheetState,
                uiState = uiState,
                navigateToExpenseDetails = navigateToExpenseDetails
            )
        }
    }
}

@Composable
fun EmptyBottomSheet() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "No expenses yet",
            fontSize = 32.sp,
            fontFamily = SourceSans3,
            fontWeight = FontWeight.Bold,
            color = DarkGray
        )
        Image(
            painter = painterResource(id = R.drawable.empty_box),
            contentDescription = null,
            modifier = Modifier.size(256.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ItemsList(
    sheetState: BottomSheetState,
    uiState: HomeUiState,
    navigateToExpenseDetails: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        val groupedExpenses: Map<LocalDate, List<Expense>> = uiState.groupedByDate
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            groupedExpenses.forEach { (date, expenses) ->
                DayItem(
                    date = date,
                    expenses = expenses,
                    uiState = uiState,
                    navigateToExpenseDetails = navigateToExpenseDetails
                )
                Spacer(modifier = Modifier.height(20.dp))
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
    expenses: List<Expense>,
    uiState: HomeUiState,
    navigateToExpenseDetails: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .background(LightYellow)
    ) {
        val formatterCurrentYear = DateTimeFormatter.ofPattern("MMMM dd")
        val formatterPastYears = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
        val formattedDate = when {
            date == LocalDate.now() -> "Today"
            date == LocalDate.now().minusDays(1) -> "Yesterday"
            date.year != LocalDate.now().year -> date.format(formatterPastYears)
            else -> date.format(formatterCurrentYear)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formattedDate,
                fontSize = 20.sp,
                fontFamily = SourceSans3,
                fontWeight = FontWeight.Bold,
                color = DarkGray,
                modifier = Modifier.padding(start = 20.dp, top = 15.dp, bottom = 5.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            if (uiState.dayExpenseCount(date.dayOfMonth, date.month, date.year) > 1) {
                Text(
                    text = String.format(
                        "$%.2f",
                        uiState.dayExpenseAmount(date.dayOfMonth, date.month, date.year)
                    ),
                    fontSize = 14.sp,
                    fontFamily = SourceSans3,
                    fontWeight = FontWeight.Medium,
                    color = Gray,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
        }
        Column {
            expenses.forEachIndexed { index, expense ->
                ExpenseItem(
                    expense = expense,
                    navigateToExpenseDetails = navigateToExpenseDetails
                )
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
    expense: Expense,
    navigateToExpenseDetails: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightYellow)
            .clickable { expense.id?.let { navigateToExpenseDetails(it) } }
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
        Spacer(modifier = Modifier.width(10.dp))
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
        Spacer(modifier = Modifier.weight(1f))
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
    scaffoldState: BottomSheetScaffoldState,
    uiState: HomeUiState,
    addExpenseItem: () -> Unit,
    clearAllExpenseItems: () -> Unit
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
                color = LightYellow
            )
            Text(
                text = String.format("$%.2f", uiState.monthExpenseAmount()),
                fontSize = 58.sp,
                fontFamily = SourceSans3,
                fontWeight = FontWeight.Bold,
                color = LightYellow,
                modifier = Modifier.clickable { addExpenseItem() }
            )
            Text(
                text = "Clear All",
                fontSize = 16.sp,
                fontFamily = SourceSans3,
                fontWeight = FontWeight.Bold,
                color = LightYellow,
                modifier = Modifier.clickable { clearAllExpenseItems() }
            )
        }
    }
}