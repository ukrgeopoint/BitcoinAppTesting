package com.example.bitcoinapptesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class ViewModelTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun getSuccessResponse() {
        val repository = Mockito.mock(Repository::class.java)
        val successfulResponse = BitcoinResponse(Data( "100 000"))

        val viewModel = MyViewModel(repository)

        var eventList = mutableListOf<MyViewModel.UIState>()
        viewModel.uiState.observeForever {
            eventList.add(it)
        }
        runBlocking {
            Mockito.`when`(repository.getCurrencyByName()).thenReturn(successfulResponse)
        }

        viewModel.getData()
        Assert.assertEquals(MyViewModel.UIState.NoData, eventList[0])
        Assert.assertEquals(MyViewModel.UIState.Processing, eventList[1])
        val bitcoinState = eventList[2] as MyViewModel.UIState.Result
        Assert.assertEquals("100 000", bitcoinState.result)
    }
}