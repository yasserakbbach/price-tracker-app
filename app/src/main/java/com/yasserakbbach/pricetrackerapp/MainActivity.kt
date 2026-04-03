package com.yasserakbbach.pricetrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yasserakbbach.pricetrackerapp.stockdetails.ui.StockDetailsRoute
import com.yasserakbbach.pricetrackerapp.stockdetails.ui.stockDetailsRoute
import com.yasserakbbach.pricetrackerapp.stockslist.ui.StocksListRoute
import com.yasserakbbach.pricetrackerapp.stockslist.ui.stocksListRoute
import com.yasserakbbach.pricetrackerapp.ui.theme.PriceTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PriceTrackerAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = StocksListRoute,
                ) {
                    stocksListRoute(
                        navigateToStockDetails = { symbol ->
                            navController.navigate(StockDetailsRoute(symbol = symbol))
                        }
                    )
                    stockDetailsRoute()
                }
            }
        }
    }
}