package com.yasserakbbach.myapplication.stockslist.ui

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object StocksListRoute

fun NavGraphBuilder.stocksListRoute(
    navigateToStockDetails: (symbol: String) -> Unit,
) {
    composable<StocksListRoute> {
        val viewModel = hiltViewModel<StocksListViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        StocksListScreen(state = state) { event ->
            when (event) {
                is StocksListEvent.OnStockClick -> navigateToStockDetails(event.stock.symbol)
                is StocksListEvent.OnToggleConnectivity -> viewModel.toggleConnectivity(event.isConnected)
            }
        }
    }
}