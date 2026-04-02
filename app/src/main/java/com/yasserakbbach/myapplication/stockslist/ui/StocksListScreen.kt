package com.yasserakbbach.myapplication.stockslist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yasserakbbach.myapplication.R
import com.yasserakbbach.myapplication.stockslist.domain.model.Stock
import com.yasserakbbach.myapplication.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StocksListScreen(
    state: StocksListState,
    event: (StocksListEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    Row (
                        modifier = Modifier.padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ){
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = stringResource(id = R.string.available_stocks),
                            style = Typography.bodyLarge,
                        )
                        if (state.isLoading) {
                            LinearProgressIndicator()
                        }
                    }
                }
                items(state.stocksList) { stock ->
                    StockItem(
                        stock = stock,
                        onClick = { event(StocksListEvent.OnStockClick(stock)) },
                    )
                }
            }
        },
        bottomBar = {

        },
    )
}

@Composable
@Preview
private fun StocksListScreenPreview() {
    StocksListScreen(
        state = StocksListState(
            stocksList = listOf(
                Stock(symbol = "NVDA", openPrice = 10.0, currentPrice = 11.0, change = 0.0, changePercent = 1.0),
                Stock(symbol = "GOOGL", openPrice = 20.0, currentPrice = 12.0, change = 0.0, changePercent = 2.0),
                Stock(symbol = "AAPL", openPrice = 30.0, currentPrice = 13.0, change = 0.0, changePercent = 3.0),
                Stock(symbol = "MSFT", openPrice = 40.0, currentPrice = 14.0, change = 0.0, changePercent = 4.0),
                Stock(symbol = "AMZN", openPrice = 50.0, currentPrice = 15.0, change = 0.0, changePercent = 5.0),
            ),
        ),
        event = { },
    )
}