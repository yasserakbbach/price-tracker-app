package com.yasserakbbach.myapplication.stockslist.domain.repository

import com.yasserakbbach.myapplication.stockslist.domain.model.SocketStatus
import com.yasserakbbach.myapplication.stockslist.domain.model.Stock
import kotlinx.coroutines.flow.Flow

interface StocksRepository {
    val socketStatus: Flow<SocketStatus>
    fun getAvailableStocks(): Flow<List<Stock>>

    fun startFeed()
    fun stopFeed()
}