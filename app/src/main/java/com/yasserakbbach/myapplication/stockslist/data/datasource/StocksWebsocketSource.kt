package com.yasserakbbach.myapplication.stockslist.data.datasource

import android.util.Log
import com.yasserakbbach.myapplication.stockslist.data.di.ApplicationScope
import com.yasserakbbach.myapplication.stockslist.domain.model.SocketStatus
import com.yasserakbbach.myapplication.stockslist.domain.model.Stock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

private const val WEBSOCKET_URL = "wss://ws.postman-echo.com/raw"
private const val TAG = "StocksWebsocketSource"
private const val FETCHING_DELAY = 2_000L

@Singleton
class StocksWebsocketSource @Inject constructor(
    private val client: OkHttpClient,
    private val fakeStocksDataSource: FakeStocksDataSource,
    @ApplicationScope private val scope: CoroutineScope,
) {
    private var webSocket: WebSocket? = null
    private val _event = Channel<SocketStatus>()
    val event = _event.receiveAsFlow()

    private val _stocks = MutableStateFlow(fakeStocksDataSource.stocks)
    val stocks = _stocks.asStateFlow()

    fun connect() {
        val request = Request.Builder()
            .url(WEBSOCKET_URL)
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "onOpen: response = ${response.message}")
                _event.trySend(SocketStatus.Connected)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d(TAG, "onMessage: text = $text")
                _event.trySend(SocketStatus.Message(payload = text))
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                Log.d(TAG, "onClosing: reason = $reason")
                _event.trySend(SocketStatus.Closing(reason = reason))
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "onClosed: reason = $reason")
                _event.trySend(SocketStatus.Closed)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "onFailure: response = ${response?.message}", t)
                _event.trySend(SocketStatus.Error(throwable = t))
            }
        }

        webSocket = client.newWebSocket(request, listener)
        startFeed()
    }

    private fun startFeed() {
        scope.launch {
            while (true) {
                delay(FETCHING_DELAY)
                val jsonStocksList = Json.encodeToString<List<Stock>>(fakeStocksDataSource.stocks)
                sendMessage(message = jsonStocksList)
            }
        }
    }

    private fun sendMessage(message: String) {
        val isSent = webSocket?.send(message) ?: false
        if (isSent) {
            Log.d(TAG, "Sent: $message")
            val decodedStockJsonList = Json.decodeFromString<List<Stock>>(message)
            _stocks.value = decodedStockJsonList
        } else {
            _event.trySend(SocketStatus.Error(throwable = Exception("Failed to send message")))
        }
    }

    fun disconnect() {
        webSocket?.close(1000, "User disconnected")
        _event.trySend(SocketStatus.Disconnected)
        webSocket = null
    }
}