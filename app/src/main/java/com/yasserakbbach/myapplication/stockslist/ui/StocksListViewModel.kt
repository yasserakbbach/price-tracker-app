package com.yasserakbbach.myapplication.stockslist.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StocksListViewModel : ViewModel() {
    private val _state = MutableStateFlow(StocksListState())
    val state = _state.asStateFlow()
}