package com.yasserakbbach.pricetrackerapp.stockslist.ui

import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.Stock

sealed interface StocksListEvent {
    data class OnStockClick(val stock: Stock) : StocksListEvent
    data class OnToggleConnectivity(val isConnected: Boolean) : StocksListEvent
}
