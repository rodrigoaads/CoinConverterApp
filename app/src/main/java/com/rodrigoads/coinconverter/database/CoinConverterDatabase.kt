package com.rodrigoads.coinconverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rodrigoads.coinconverter.database.daos.BitcoinDao
import com.rodrigoads.coinconverter.database.daos.CurrencyDao
import com.rodrigoads.coinconverter.database.daos.StockDao
import com.rodrigoads.coinconverter.database.entities.Bitcoin
import com.rodrigoads.coinconverter.database.entities.Currency
import com.rodrigoads.coinconverter.database.entities.Stock

@Database(entities = [Currency::class, Bitcoin::class, Stock::class], version = 1)
abstract class CoinConverterDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun bitcoinDao(): BitcoinDao
    abstract fun stockDao(): StockDao

    companion object {
        private const val DATABASE_NAME: String = "coin_converter_db"

        @Volatile
        private var INSTANCE: CoinConverterDatabase? = null

        fun getDatabase(context: Context): CoinConverterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinConverterDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}