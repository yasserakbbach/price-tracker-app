package com.yasserakbbach.pricetrackerapp.stockslist.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    val symbol: String,
    val openPrice: Double,
    val change: Double,
    val changePercent: Double,
) {
    val isIncreased: Boolean
        get() = change > 0
}
