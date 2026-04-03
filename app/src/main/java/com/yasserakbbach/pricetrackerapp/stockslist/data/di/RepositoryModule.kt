package com.yasserakbbach.pricetrackerapp.stockslist.data.di

import com.yasserakbbach.pricetrackerapp.stockslist.data.repository.StocksRepositoryImpl
import com.yasserakbbach.pricetrackerapp.stockslist.domain.repository.StocksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindStocksRepository(stocksRepositoryImpl: StocksRepositoryImpl) : StocksRepository
}