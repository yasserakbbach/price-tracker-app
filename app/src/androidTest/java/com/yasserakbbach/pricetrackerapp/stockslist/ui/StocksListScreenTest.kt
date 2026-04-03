package com.yasserakbbach.pricetrackerapp.stockslist.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yasserakbbach.pricetrackerapp.stockslist.domain.model.Stock
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StocksListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Dummy data for testing
    private val testStocks = persistentListOf(
        Stock(symbol = "AAPL", openPrice = 150.0, change = 1.5, changePercent = 1.0),
        Stock(symbol = "GOOGL", openPrice = 2800.0, change = -12.0, changePercent = -0.4)
    )

    @Test
    fun displays_online_status_when_state_is_online() {
        val state = StocksListState(isOnline = true, stocksList = persistentListOf())

        composeTestRule.setContent {
            StocksListScreen(state = state, event = {})
        }

        // Assuming your strings.xml has "Connected"
        composeTestRule.onNodeWithText("Connected").assertIsDisplayed()

        // Find the switch. Usually better to use a testTag, but we can find it by role or simply checking toggleable nodes
        composeTestRule.onNodeWithTag("ToggleConnectivitySwitch", useUnmergedTree = true)
            .assertExists()
            .assertIsOn() // Because isOnline = true
    }

    @Test
    fun displays_offline_status_when_state_is_offline() {
        val state = StocksListState(isOnline = false, stocksList = persistentListOf())

        composeTestRule.setContent {
            StocksListScreen(state = state, event = {})
        }

        composeTestRule.onNodeWithText("Disconnected").assertIsDisplayed()

        // Verifying the switch is off
        composeTestRule.onNodeWithTag("ToggleConnectivitySwitch", useUnmergedTree = true)
            .assertExists()
            .assertIsOff()
    }

    @Test
    fun displays_loading_indicator_when_loading_is_true() {
        val state = StocksListState(isLoading = true, stocksList = persistentListOf())

        composeTestRule.setContent {
            StocksListScreen(state = state, event = {})
        }

        // Testing for LinearProgressIndicator. Using a testTag is the most robust way.
        composeTestRule.onNodeWithTag("StocksLoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun renders_stock_list_correctly() {
        val state = StocksListState(stocksList = testStocks)

        composeTestRule.setContent {
            StocksListScreen(state = state, event = {})
        }

        // Verify the static text exists
        composeTestRule.onNodeWithText("Available stocks", useUnmergedTree = true).assertIsDisplayed()

        // Verify that items are rendered. This assumes StockItem displays the symbol.
        composeTestRule.onNodeWithText("AAPL").assertIsDisplayed()
        composeTestRule.onNodeWithText("GOOGL").assertIsDisplayed()
    }

    @Test
    fun toggling_switch_triggers_connectivity_event() {
        var capturedEvent: StocksListEvent? = null
        val state = StocksListState(isOnline = true, stocksList = persistentListOf())

        composeTestRule.setContent {
            StocksListScreen(
                state = state,
                event = { capturedEvent = it }
            )
        }

        // Act: Click the switch to toggle it
        composeTestRule.onNodeWithTag("ToggleConnectivitySwitch").performClick()

        // Assert: Verify the event was fired with the correct payload (false, because it was true)
        assertTrue(capturedEvent is StocksListEvent.OnToggleConnectivity)
        assertEquals(false, (capturedEvent as StocksListEvent.OnToggleConnectivity).isConnected)
    }

    @Test
    fun clicking_stock_item_triggers_stock_click_event() {
        var capturedEvent: StocksListEvent? = null
        val state = StocksListState(stocksList = testStocks)

        composeTestRule.setContent {
            StocksListScreen(
                state = state,
                event = { capturedEvent = it }
            )
        }

        // Act: Click on the specific stock item
        composeTestRule.onNodeWithText("AAPL").performClick()

        // Assert: Verify the event fired with the correct stock
        assertTrue(capturedEvent is StocksListEvent.OnStockClick)
        assertEquals("AAPL", (capturedEvent as StocksListEvent.OnStockClick).stock.symbol)
    }
}