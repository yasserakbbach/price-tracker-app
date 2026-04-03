package com.yasserakbbach.pricetrackerapp.stockdetails.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yasserakbbach.pricetrackerapp.stockslist.domain.repository.StocksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stocksRepository: StocksRepository,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<StockDetailsRoute>()

    private val _state = MutableStateFlow<StockDetailsScreenState>(StockDetailsScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        loadStockDetails()
    }

    private fun loadStockDetails() {
        _state.update {
            StockDetailsScreenState.Loading
        }
        viewModelScope.launch {
            stocksRepository.findStockBySymbol(symbol = route.symbol).collect {
                it?.let { stock ->
                    _state.update {
                        StockDetailsScreenState.Success(stock = stock)
                    }
                } ?: run {
                    _state.update {
                        StockDetailsScreenState.Error
                    }
                }
            }
        }
    }
}