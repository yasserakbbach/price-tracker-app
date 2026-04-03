package com.yasserakbbach.pricetrackerapp.stockslist.ui

import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.Stock
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class StocksListState(
    val stocksList: ImmutableList<Stock> = persistentListOf(),
    val isLoading: Boolean = false,
    val isOnline: Boolean = true,
)
