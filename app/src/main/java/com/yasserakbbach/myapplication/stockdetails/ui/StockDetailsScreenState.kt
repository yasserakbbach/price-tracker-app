package com.yasserakbbach.myapplication.stockdetails.ui

import com.yasserakbbach.myapplication.stockslist.domain.model.Stock

sealed interface StockDetailsScreenState {
    data object Loading : StockDetailsScreenState
    data object Error : StockDetailsScreenState
    data class Success(val stock: Stock) : StockDetailsScreenState
}
