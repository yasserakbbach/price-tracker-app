package com.yasserakbbach.myapplication.stockslist.data.datasource

import com.yasserakbbach.myapplication.stockslist.domain.model.Stock
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class FakeStocksDataSource @Inject constructor() {
    val stocks: List<Stock>
        get() = listOf(
            generateRandomStockBySymbol(symbol = "NVDA"),
            generateRandomStockBySymbol(symbol = "GOOGL"),
            generateRandomStockBySymbol(symbol = "AAPL"),
            generateRandomStockBySymbol(symbol = "MSFT"),
            generateRandomStockBySymbol(symbol = "AMZN"),
            generateRandomStockBySymbol(symbol = "2222.SR"),
            generateRandomStockBySymbol(symbol = "TSM"),
            generateRandomStockBySymbol(symbol = "AVGO"),
            generateRandomStockBySymbol(symbol = "META"),
            generateRandomStockBySymbol(symbol = "TSLA"),
            generateRandomStockBySymbol(symbol = "BRK.B"),
            generateRandomStockBySymbol(symbol = "WMT"),
            generateRandomStockBySymbol(symbol = "005930.KS"),
            generateRandomStockBySymbol(symbol = "005930.KS"),
            generateRandomStockBySymbol(symbol = "JNJ"),
            generateRandomStockBySymbol(symbol = "TCEHY"),
            generateRandomStockBySymbol(symbol = "V"),
            generateRandomStockBySymbol(symbol = "ASML"),
            generateRandomStockBySymbol(symbol = "COST"),
            generateRandomStockBySymbol(symbol = "MA"),
            generateRandomStockBySymbol(symbol = "ORCL"),
            generateRandomStockBySymbol(symbol = "MU"),
            generateRandomStockBySymbol(symbol = "1398.HK"),
        )

    private fun generateRandomStockBySymbol(symbol: String): Stock {
        val openPrice = Random.nextDouble(100.0, 200.0)
        val currentPrice = Random.nextDouble(100.0, 200.0)
        val change = currentPrice - openPrice
        val changePercentage = (change / openPrice) * 100
        return Stock(
            symbol = symbol,
            openPrice = openPrice,
            change = change,
            changePercent = changePercentage,
        )
    }
}