package com.yasserakbbach.myapplication.stockslist.ui

import com.yasserakbbach.myapplication.stockslist.domain.model.Stock

sealed interface StocksListEvent {
    data class OnStockClick(val stock: Stock) : StocksListEvent
}
