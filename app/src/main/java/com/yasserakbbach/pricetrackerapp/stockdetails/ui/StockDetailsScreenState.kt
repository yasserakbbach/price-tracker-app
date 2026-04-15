package com.yasserakbbach.pricetrackerapp.stockdetails.ui

import androidx.compose.runtime.Immutable
import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.Stock

@Immutable
sealed interface StockDetailsScreenState {
    data object Loading : StockDetailsScreenState
    data object Error : StockDetailsScreenState
    data class Success(val stock: Stock) : StockDetailsScreenState
}
