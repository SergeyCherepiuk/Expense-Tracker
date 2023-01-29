package com.example.expensetracker.utils

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.material.ExperimentalMaterialApi

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val currentValue = bottomSheetState.currentValue
        val targetValue = bottomSheetState.targetValue

        return when {
            currentValue == Expanded && targetValue == Expanded && fraction == 1f -> 0f
            currentValue == Collapsed && targetValue == Collapsed && fraction == 1f -> 1f
            currentValue == Collapsed -> 1f - fraction
            else -> fraction
        }
    }