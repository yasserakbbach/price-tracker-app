package com.yasserakbbach.myapplication.stockdetails.ui

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable

@Serializable
data class StockDetailsRoute(val symbol: String)

fun NavGraphBuilder.stockDetailsRoute() {
    composable<StockDetailsRoute>(
        deepLinks = listOf(
            navDeepLink<StockDetailsRoute>(
                basePath = "stocks://symbol"
            ) {
                action = Intent.ACTION_VIEW
            }
        )
    ) {
        val viewModel = hiltViewModel<StockDetailsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        StockDetailsScreen(state = state)
    }
}