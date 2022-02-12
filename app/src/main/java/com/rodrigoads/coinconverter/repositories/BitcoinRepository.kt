package com.rodrigoads.coinconverter.repositories

import com.rodrigoads.coinconverter.api.ApiService
import com.rodrigoads.coinconverter.database.CoinConverterDatabase
import com.rodrigoads.coinconverter.database.entities.Bitcoin
import com.rodrigoads.coinconverter.model.api.bitcoin.SourceRequestBitcoinModel
import com.rodrigoads.coinconverter.repositories.sealed.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.lang.Exception

class BitcoinRepository(private val coinConverterDatabase: CoinConverterDatabase) {
    val getBitcoin = coinConverterDatabase.bitcoinDao().getBitcoin()

    suspend fun insertBitcoin(bitcoinList: List<Bitcoin>) {
        coinConverterDatabase.bitcoinDao().insertBitcoin(bitcoinList)
    }

    suspend fun removerAllBitcoin() {
        coinConverterDatabase.bitcoinDao().removeAllBitcoin()
    }
}

class BitcoinApiResponseRepository(private val retrofitRequest: ApiService) {
    suspend fun getBitcoin(field: String): Result<SourceRequestBitcoinModel?> {
        return withContext(IO) {
            val request = retrofitRequest.retrofitService.getBitCoin(field)
            if (request.isSuccessful) {
                Result.Success(data = request.body())
            } else {
                Result.Error(Exception())
            }
        }
    }
}