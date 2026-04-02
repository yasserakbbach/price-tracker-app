package com.yasserakbbach.myapplication.stockdetails.ui

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class StockDetailsRoute(val symbol: String)

fun NavGraphBuilder.stockDetailsRoute() {
    composable<StockDetailsRoute> {
        val viewModel = hiltViewModel<StockDetailsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        StockDetailsScreen(state = state)
    }
}