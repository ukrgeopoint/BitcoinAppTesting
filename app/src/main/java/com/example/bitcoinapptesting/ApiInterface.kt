package com.example.bitcoinapptesting

import retrofit2.http.GET

interface ApiInterface {
    @GET("/v2/rates/bitcoin")
    suspend fun getCryptoByName(): BitcoinResponse
}