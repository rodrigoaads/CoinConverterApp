package com.rodrigoads.coinconverter.application

import android.app.Application
import com.rodrigoads.coinconverter.database.CoinConverterDatabase
import com.rodrigoads.coinconverter.repositories.BitcoinRepository
import com.rodrigoads.coinconverter.repositories.CurrencyRepository
import com.rodrigoads.coinconverter.repositories.StockRepository

class CoinConverterApplication : Application() {
    val database by lazy { CoinConverterDatabase.getDatabase(this) }
    val currencyRepository by lazy { CurrencyRepository(database) }
    val bitcoinRepository by lazy { BitcoinRepository(database) }
    val stockRepository by lazy { StockRepository(database) }
}