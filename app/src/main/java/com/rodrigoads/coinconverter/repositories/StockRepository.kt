package com.rodrigoads.coinconverter.repositories

import com.rodrigoads.coinconverter.api.ApiService
import com.rodrigoads.coinconverter.database.CoinConverterDatabase
import com.rodrigoads.coinconverter.database.entities.Stock
import com.rodrigoads.coinconverter.repositories.sealed.Result
import com.rodrigoads.coinconverter.model.api.stock.SourceRequestStockModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.lang.Exception

class StockRepository(private val database: CoinConverterDatabase) {
    val getStocks = database.stockDao().getStocks()

    suspend fun insertStocks(stocksList: List<Stock>) {
        database.stockDao().insertStocks(stocksList)
    }

    suspend fun removeAllStocks() {
        database.stockDao().removeAllStocks()
    }
}

class StockApiResponseRepository(private val retrofitRequest: ApiService) {
    suspend fun getStocks(field: String): Result<SourceRequestStockModel?> {
        return withContext(IO) {
            val request = retrofitRequest.retrofitService.getStocks(field)
            if (request.isSuccessful) {
                Result.Success(data = request.body())
            } else {
                Result.Error(Exception())
            }
        }
    }
}