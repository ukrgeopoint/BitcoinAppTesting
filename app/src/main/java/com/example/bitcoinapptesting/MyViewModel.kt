package com.example.bitcoinapptesting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableLiveData<UIState>(UIState.NoData)
    val uiState: LiveData<UIState> = _uiState

    private val repo = repository

    fun getData() {
        _uiState.value = UIState.Processing

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repo.getCurrencyByName()
                if (response.data != null) {
                    _uiState.postValue(
                        UIState.Result("${response.data.rateUsd}")
                    )
                } else _uiState.postValue(UIState.Error("Error"))
            }
        }
    }


    sealed class UIState {
        data object NoData : UIState()
        data object Processing : UIState()
        class Result(val result: String) : UIState()
        class Error(val description: String) : UIState()
    }

}