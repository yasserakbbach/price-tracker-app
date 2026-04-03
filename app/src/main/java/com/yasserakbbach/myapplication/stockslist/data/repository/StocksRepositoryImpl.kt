package com.yasserakbbach.myapplication.stockslist.data.repository

import com.yasserakbbach.myapplication.stockslist.data.datasource.StocksWebsocketSource
import com.yasserakbbach.myapplication.stockslist.domain.model.SocketStatus
import com.yasserakbbach.myapplication.stockslist.domain.model.Stock
import com.yasserakbbach.myapplication.stockslist.domain.repository.StocksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StocksRepositoryImpl @Inject constructor(
    private val stocksWebsocketSource: StocksWebsocketSource,
) : StocksRepository {

    override val socketStatus: Flow<SocketStatus>
        get() = stocksWebsocketSource.event

    override fun getAvailableStocks(): Flow<List<Stock>> {
        return stocksWebsocketSource.stocks
    }

    override suspend fun startFeed() {
        stocksWebsocketSource.connect()
    }

    override fun stopFeed() {
        stocksWebsocketSource.disconnect()
    }

    override fun findStockBySymbol(symbol: String): Flow<Stock?> =
        stocksWebsocketSource.findStockBySymbol(symbol)
}