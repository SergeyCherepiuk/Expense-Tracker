package com.example.expensetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.expensetracker.models.Item
import com.example.expensetracker.ui.components.HeaderInfo
import com.example.expensetracker.ui.components.ItemsList
import com.example.expensetracker.ui.theme.LightGray200
import com.example.expensetracker.utils.Constants
import com.example.expensetracker.utils.currentFraction
import com.example.expensetracker.viewmodels.ItemViewModel
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemsListScreen(viewModel: ItemViewModel) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
        animationSpec = tween(
            durationMillis = 500,
            easing = CubicBezierEasing(a = 0.1f, b = 0.75f,c = 0.3f,d = 1f)
        )
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val items = viewModel.items.collectAsState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 450.dp,
        sheetElevation = 20.dp,
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        sheetContent = {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                ItemsList(
                    items = items.value,
                    sheetState = sheetState
                )
            }
        }
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .alpha(scaffoldState.currentFraction)
                .offset(y = -(1f - scaffoldState.currentFraction).dp * 100)
                .background(LightGray200)
        ) {
            val balance: Double = items.value.sumOf { item -> item.price }
            HeaderInfo(balance, scaffoldState.currentFraction)

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(onClick = {
                    viewModel.addItem(Item(
                        id = null,
                        emoji = Constants.emojis[Random.nextInt(until = Constants.emojis.size-1)],
                        text = "Text",
                        category = "Category",
                        price = Random.nextDouble(from = 0.01, until = 500.0),
                        date = Constants.dates[Random.nextInt(until = Constants.dates.size-1)]
                    ))
                }) {
                    Text(text = "Add item")
                }
                Button(onClick = {
                    viewModel.clear()
                }) {
                    Text(text = "Clear")
                }
            }
        }
    }
}