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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.yasserakbbach.pricetrackerapp.ui.theme.ConnectedColor
import com.yasserakbbach.pricetrackerapp.ui.theme.DisconnectedColor
import com.yasserakbbach.pricetrackerapp.ui.theme.StockDescriptionStyle
import com.yasserakbbach.pricetrackerapp.ui.theme.StockSymbolStyle
import java.util.Locale

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
                    .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val color = if (stock.isIncreased) {
                    ConnectedColor
                } else {
                    DisconnectedColor
                }

                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = "${String.format(Locale.getDefault(), "%.2f", stock.change)}$",
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
                        This stock's opening price is ${String.format(Locale.getDefault(), "%.2f", stock.openPrice)}$
                        and the percentage of its progress is ${String.format(Locale.getDefault(), "%.2f", stock.changePercent)}%
                    """.trimIndent(),
                    style = StockDescriptionStyle,
                )
            }
        },
    )
}

@Composable
private fun StockDetailsScreenError() {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White),
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
            style = StockSymbolStyle.copy(color = DisconnectedColor),
        )
    }
}

@Composable
private fun StockDetailsScreenLoading() {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White),
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