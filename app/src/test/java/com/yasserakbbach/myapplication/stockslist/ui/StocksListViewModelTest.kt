package com.yasserakbbach.myapplication.stockslist.ui

import com.yasserakbbach.myapplication.stockslist.domain.model.SocketStatus
import com.yasserakbbach.myapplication.stockslist.domain.model.Stock
import com.yasserakbbach.myapplication.stockslist.domain.repository.StocksRepository
import com.yasserakbbach.myapplication.util.MainDispatcherRule
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StocksListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: StocksRepository
    private lateinit var viewModel: StocksListViewModel

    // Use SharedFlows to simulate emissions from the repository over time
    private lateinit var socketStatusFlow: MutableSharedFlow<SocketStatus>
    private lateinit var stocksFlow: MutableSharedFlow<List<Stock>>

    @Before
    fun setUp() {
        socketStatusFlow = MutableSharedFlow()
        stocksFlow = MutableSharedFlow()

        repository = mockk {
            every { socketStatus } returns socketStatusFlow
            every { getAvailableStocks() } returns stocksFlow
            every { startFeed() } just Runs
            every { stopFeed() } just Runs
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `init starts feed and observes flows`() = runTest {
        // Act
        viewModel = StocksListViewModel(repository)

        // Assert
        verify(exactly = 1) { repository.socketStatus }
        verify(exactly = 1) { repository.getAvailableStocks() }
        verify(exactly = 1) { repository.startFeed() }
    }

    @Test
    fun `when socket status emits Connected, state isOnline updates to true`() = runTest {
        // Arrange
        viewModel = StocksListViewModel(repository)

        // Act
        socketStatusFlow.emit(SocketStatus.Connected)

        // Assert
        assertTrue(viewModel.state.value.isOnline)
    }

    @Test
    fun `when socket status emits Disconnected, state isOnline updates to false`() = runTest {
        // Arrange
        viewModel = StocksListViewModel(repository)
        // First set to true to ensure we are actually testing the change to false
        socketStatusFlow.emit(SocketStatus.Connected)

        // Act
        socketStatusFlow.emit(SocketStatus.Disconnected)

        // Assert
        assertFalse(viewModel.state.value.isOnline)
    }

    @Test
    fun `when socket status emits Error, state isOnline updates to false`() = runTest {
        // Arrange
        viewModel = StocksListViewModel(repository)
        socketStatusFlow.emit(SocketStatus.Connected)

        // Act
        socketStatusFlow.emit(SocketStatus.Error(Exception("Network failure")))

        // Assert
        assertFalse(viewModel.state.value.isOnline)
    }

    @Test
    fun `when stocks are emitted, state updates and sorts them descending by change`() = runTest {
        // Arrange
        viewModel = StocksListViewModel(repository)
        val stock1 = Stock(symbol = "AAPL", change = 1.5, openPrice = 1.0, changePercent = .2)
        val stock2 = Stock(symbol = "TSLA", change = 5.0, openPrice = 1.0, changePercent = .2)
        val stock3 = Stock(symbol = "GOOGL", change = -2.0, openPrice = 1.0, changePercent = -1.0)

        val unsortedList = listOf(stock1, stock2, stock3)

        // Act
        stocksFlow.emit(unsortedList)

        // Assert
        val stateStocks = viewModel.state.value.stocksList
        assertEquals(3, stateStocks.size)
        // Verify it sorted descending (Highest change first)
        assertEquals(stock2, stateStocks[0]) // 5.0
        assertEquals(stock1, stateStocks[1]) // 1.5
        assertEquals(stock3, stateStocks[2]) // -2.0
    }

    @Test
    fun `toggleConnectivity true calls startFeed`() {
        // Arrange
        viewModel = StocksListViewModel(repository)

        // Act
        viewModel.toggleConnectivity(true)

        // Assert
        // Expected 2 times: once during init block, once during toggle
        verify(exactly = 2) { repository.startFeed() }
        verify(exactly = 0) { repository.stopFeed() }
    }

    @Test
    fun `toggleConnectivity false calls stopFeed`() {
        // Arrange
        viewModel = StocksListViewModel(repository)

        // Act
        viewModel.toggleConnectivity(false)

        // Assert
        verify(exactly = 1) { repository.stopFeed() }
    }
}