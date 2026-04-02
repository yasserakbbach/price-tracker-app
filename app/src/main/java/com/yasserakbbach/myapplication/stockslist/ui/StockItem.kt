package com.yasserakbbach.myapplication.stockslist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yasserakbbach.myapplication.R
import com.yasserakbbach.myapplication.stockslist.domain.model.Stock
import com.yasserakbbach.myapplication.ui.theme.StockChangeStyle
import com.yasserakbbach.myapplication.ui.theme.StockSymbolStyle

@Composable
fun StockItem(
    modifier: Modifier = Modifier,
    stock: Stock,
    onClick: (Stock) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(50.dp),
        onClick = { onClick(stock)}
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stock.symbol,
                style = StockSymbolStyle,
            )
            Row(
                modifier = Modifier.padding(end = 16.dp)
                    .align(Alignment.CenterEnd),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = if (stock.isIncreased) {
                            R.drawable.arrow_up
                        } else {
                            R.drawable.arrow_down
                        }
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
                Text(
                    text = stock.change.toString(),
                    style = StockChangeStyle.copy(
                        color = if (stock.isIncreased) {
                            Color.Green
                        } else {
                            Color.Red
                        },
                    ),
                )
            }
        }
    }
}

@Composable
@Preview
private fun StockItemPreview() {
    StockItem(
        stock = Stock(
            symbol = "GOOGL",
            openPrice = 294.00,
            currentPrice = 294.00,
            change = 0.0,
            changePercent = 1.0,
        ),
        onClick = { },
    )
}