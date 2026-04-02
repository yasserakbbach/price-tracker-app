package com.yasserakbbach.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yasserakbbach.myapplication.stockdetails.ui.StockDetailsRoute
import com.yasserakbbach.myapplication.stockdetails.ui.stockDetailsRoute
import com.yasserakbbach.myapplication.stockslist.ui.StocksListRoute
import com.yasserakbbach.myapplication.stockslist.ui.stocksListRoute
import com.yasserakbbach.myapplication.ui.theme.PriceTrackerAppTheme
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