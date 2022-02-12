package com.rodrigoads.coinconverter.api

import com.rodrigoads.coinconverter.model.api.bitcoin.SourceRequestBitcoinModel
import com.rodrigoads.coinconverter.model.api.currency.SourceRequestCurrencyModel
import com.rodrigoads.coinconverter.model.api.stock.SourceRequestStockModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.hgbrasil.com/"
const val API_KEY = "SUA-CHAVE"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface CoinApiService {
    @GET("finance")
    suspend fun getCurrencies(
        @Query("fields") field: String,
        @Query("key") key: String = API_KEY
    ): Response<SourceRequestCurrencyModel>

    @GET("finance")
    suspend fun getStocks(
        @Query("fields") field: String,
        @Query("key") key: String = API_KEY
    ): Response<SourceRequestStockModel>

    @GET("finance")
    suspend fun getBitCoin(
        @Query("fields") field: String,
        @Query("key") key: String = API_KEY
    ): Response<SourceRequestBitcoinModel>
}

object ApiService {
    val retrofitService: CoinApiService by lazy {
        retrofit.create(CoinApiService::class.java)
    }
}