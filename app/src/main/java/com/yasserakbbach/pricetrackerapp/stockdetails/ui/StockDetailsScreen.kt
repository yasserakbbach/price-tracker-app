package com.yasserakbbach.pricetrackerapp.stockdetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yasserakbbach.pricetrackerapp.R
import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.Stock
import com.yasserakbbach.pricetrackerapp.ui.theme.StockDescriptionStyle
import com.yasserakbbach.pricetrackerapp.ui.theme.StockSymbolStyle
import com.yasserakbbach.pricetrackerapp.ui.theme.connectedColor
import com.yasserakbbach.pricetrackerapp.ui.theme.disconnectedColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDetailsScreen(
    state: StockDetailsScreenState,
) {
    when (state) {
        StockDetailsScreenState.Loading -> StockDetailsScreenLoading()
        is StockDetailsScreenState.Error -> StockDetailsScreenError()
        is StockDetailsScreenState.Success -> StockDetailsScreenContent(stock = state.stock)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StockDetailsScreenContent(stock: Stock) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stock.symbol) })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val connected = connectedColor
                val disconnected = disconnectedColor
                val color = remember(stock.change, connected, disconnected) {
                    if (stock.isIncreased) connected else disconnected
                }
                val formattedChange = remember(stock.change) { "%.2f".format(stock.change) }
                val formattedOpenPrice = remember(stock.openPrice) { "%.2f".format(stock.openPrice) }
                val formattedChangePercent = remember(stock.changePercent) { "%.2f".format(stock.changePercent) }

                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "$formattedChange$",
                    style = StockSymbolStyle,
                )
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(
                        id = if (stock.isIncreased) {
                            R.drawable.arrow_up
                        } else {
                            R.drawable.arrow_down
                        }
                    ),
                    contentDescription = null,
                    tint = color,
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = """
                        This stock's opening price is $formattedOpenPrice$
                        and the percentage of its progress is $formattedChangePercent%
                    """.trimIndent(),
                    style = StockDescriptionStyle,
                )
            }
        },
    )
}

@Composable
private fun StockDetailsScreenError() {
    val errorTextStyle = StockSymbolStyle.copy(color = disconnectedColor)
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.error),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = stringResource(id = R.string.error_loading_stock),
            style = errorTextStyle,
        )
    }
}

@Composable
private fun StockDetailsScreenLoading() {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LinearProgressIndicator()
    }
}

@Composable
@Preview
private fun StockDetailsScreenPreview() {
    StockDetailsScreen(
        state = StockDetailsScreenState.Success(
            stock = Stock(
                symbol = "GOOGL",
                openPrice = 20.0,
                change = 35.55,
                changePercent = 2.0,
            ),
        )
    )
}