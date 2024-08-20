package com.example.bitcoinapptesting

import retrofit2.Retrofit

class Repository (private val apiClient: Retrofit) {
    suspend fun getCurrencyByName(): BitcoinResponse {
        val apiInterface = apiClient.create(ApiInterface::class.java)
        return apiInterface.getCryptoByName()
    }
}