package com.yasserakbbach.pricetrackerapp.stockdetails.ui

import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.Stock

sealed interface StockDetailsScreenState {
    data object Loading : StockDetailsScreenState
    data object Error : StockDetailsScreenState
    data class Success(val stock: Stock) : StockDetailsScreenState
}
