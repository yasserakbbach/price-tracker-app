package com.yasserakbbach.myapplication.stockslist.ui

import com.yasserakbbach.myapplication.stockslist.domain.model.Stock

data class StocksListState(
    val stocksList: List<Stock> = emptyList(),
    val isLoading: Boolean = false,
    val isOnline: Boolean = true,
)
