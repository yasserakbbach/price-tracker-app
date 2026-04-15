package com.yasserakbbach.pricetrackerapp.stockslist.ui

import androidx.compose.runtime.Immutable
import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.Stock
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class StocksListState(
    val stocksList: ImmutableList<Stock> = persistentListOf(),
    val isLoading: Boolean = false,
    val isOnline: Boolean = true,
)
