package com.yasserakbbach.pricetrackerapp.stockslist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.SocketStatus
import com.yasserakbbach.pricetrackerapp.stockslist.domain.repository.StocksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksListViewModel @Inject constructor(
    private val stocksRepository: StocksRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(StocksListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            stocksRepository.socketStatus.collect { status ->
                when (status) {
                    is SocketStatus.Connected -> {
                        _state.update { it.copy(isOnline = true) }
                    }
                    is SocketStatus.Disconnected, is SocketStatus.Error -> {
                        _state.update { it.copy(isOnline = false) }
                    }
                    else -> Unit
                }
            }
        }
        viewModelScope.launch {
            stocksRepository.getAvailableStocks()
                .map { stocks -> stocks.sortedByDescending { it.change }.toImmutableList()  }
                .flowOn(Dispatchers.Default)
                .collect { sortedStocks ->
                    _state.update { it.copy(stocksList = sortedStocks) }
                }
        }
        viewModelScope.launch {
            stocksRepository.startFeed()
        }
    }
    fun toggleConnectivity(isConnected: Boolean) {
        viewModelScope.launch {
            if (isConnected) {
                stocksRepository.startFeed()
            } else {
                stocksRepository.stopFeed()
            }
        }
    }
}