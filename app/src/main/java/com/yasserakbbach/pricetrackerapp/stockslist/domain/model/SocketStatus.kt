package com.yasserakbbach.pricetrackerapp.stockslist.domain.model

sealed interface SocketStatus {
    object Connected : SocketStatus
    object Disconnected : SocketStatus
    data class Message(val payload: String) : SocketStatus
    data class Error(val throwable: Throwable) : SocketStatus
    data class Closing(val reason: String) : SocketStatus
    data object Closed : SocketStatus
}