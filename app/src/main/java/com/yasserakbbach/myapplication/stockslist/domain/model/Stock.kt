package com.yasserakbbach.myapplication.stockslist.domain.model

data class Stock(
    val symbol: String,
    val openPrice: Double,
    val currentPrice: Double,
    val change: Double,
    val changePercent: Double,
) {
    val isIncreased: Boolean
        get() = change > 0
}
