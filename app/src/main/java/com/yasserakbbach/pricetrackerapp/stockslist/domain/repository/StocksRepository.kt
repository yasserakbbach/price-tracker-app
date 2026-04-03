package com.yasserakbbach.pricetrackerapp.stockslist.domain.repository

import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.SocketStatus
import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.Stock
import kotlinx.coroutines.flow.Flow

interface StocksRepository {
    val socketStatus: Flow<SocketStatus>
    fun getAvailableStocks(): Flow<List<Stock>>

    suspend fun startFeed()
    fun stopFeed()
    fun findStockBySymbol(symbol: String): Flow<Stock?>
}