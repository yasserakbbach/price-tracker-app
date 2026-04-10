package com.yasserakbbach.pricetrackerapp.stockslist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yasserakbbach.pricetrackerapp.R
import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.Stock
import com.yasserakbbach.pricetrackerapp.ui.theme.ConnectedColor
import com.yasserakbbach.pricetrackerapp.ui.theme.ConnectivityStatusStyle
import com.yasserakbbach.pricetrackerapp.ui.theme.DisconnectedColor
import com.yasserakbbach.pricetrackerapp.ui.theme.Typography
import kotlinx.collections.immutable.persistentListOf

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
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp),
                    ) {
                        val (color, text) = if (state.isOnline) {
                            ConnectedColor to stringResource(id = R.string.connected)
                        } else {
                            DisconnectedColor to stringResource(id = R.string.disconnected)
                        }
                        Row(
                            modifier = Modifier.align(Alignment.CenterStart),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier.size(24.dp)
                                    .background(color = color, shape = CircleShape)
                            )
                            Text(
                                modifier = Modifier.padding(start = 6.dp),
                                text = text,
                                style = ConnectivityStatusStyle,
                            )
                        }
                        Switch(
                            modifier = Modifier.align(Alignment.CenterEnd)
                                .testTag("ToggleConnectivitySwitch"),
                            checked = state.isOnline,
                            onCheckedChange = { event(StocksListEvent.OnToggleConnectivity(isConnected = it)) }
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
                    .padding(16.dp)
            ) {
                item(key = "header") {
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
                            LinearProgressIndicator(modifier = Modifier.testTag("StocksLoadingIndicator"))
                        }
                    }
                }
                items(
                    items = state.stocksList,
                    key = { stock -> stock.symbol },
                    contentType = { "stock_item" },
                ) { stock ->
                    val onStockClick: (Stock) -> Unit = remember(stock.symbol, event) {
                        { _ -> event(StocksListEvent.OnStockClick(stock)) }
                    }
                    StockItem(
                        stock = stock,
                        onClick = onStockClick,
                    )
                }
            }
        },
    )
}

@Composable
@Preview
private fun StocksListScreenPreview() {
    StocksListScreen(
        state = StocksListState(
            stocksList = persistentListOf(
                Stock(symbol = "NVDA", openPrice = 10.0, change = 11.0, changePercent = 1.0),
                Stock(symbol = "GOOGL", openPrice = 20.0, change = 12.0, changePercent = 2.0),
                Stock(symbol = "AAPL", openPrice = 30.0, change = 13.0, changePercent = 3.0),
                Stock(symbol = "MSFT", openPrice = 40.0, change = 14.0, changePercent = 4.0),
                Stock(symbol = "AMZN", openPrice = 50.0, change = 15.0, changePercent = 5.0),
            ),
        ),
        event = { },
    )
}