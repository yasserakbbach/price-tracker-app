package com.yasserakbbach.myapplication.stockslist.data.di

import com.yasserakbbach.myapplication.stockslist.data.repository.StocksRepositoryImpl
import com.yasserakbbach.myapplication.stockslist.domain.repository.StocksRepository
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