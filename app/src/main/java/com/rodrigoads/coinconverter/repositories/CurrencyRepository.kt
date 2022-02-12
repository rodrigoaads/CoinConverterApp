package com.rodrigoads.coinconverter.repositories

import com.rodrigoads.coinconverter.api.ApiService
import com.rodrigoads.coinconverter.database.CoinConverterDatabase
import com.rodrigoads.coinconverter.database.entities.Currency
import com.rodrigoads.coinconverter.model.api.currency.SourceRequestCurrencyModel
import com.rodrigoads.coinconverter.repositories.sealed.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception

class CurrencyRepository(private val database: CoinConverterDatabase) {
    val getCurrencies: Flow<List<Currency>> = database.currencyDao().getCurrencies()

    suspend fun insertCurrencies(currencyList: List<Currency>) {
        database.currencyDao().insertCurrencies(currencyList)
    }

    suspend fun removeAllCurrencies() {
        database.currencyDao().removeAllCurrencies()
    }
}

class CurrencyApiResponseRepository(private val retrofitRequest: ApiService) {
    suspend fun getCurrencies(field: String): Result<SourceRequestCurrencyModel?> {
        return withContext(IO) {
            val request = retrofitRequest.retrofitService.getCurrencies(field)
            if (request.isSuccessful) {
                Result.Success(data = request.body())
            } else {
                Result.Error(Exception())
            }
        }
    }
}