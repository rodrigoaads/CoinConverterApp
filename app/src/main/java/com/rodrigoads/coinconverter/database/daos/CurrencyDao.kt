package com.rodrigoads.coinconverter.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodrigoads.coinconverter.database.entities.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency_table")
    fun getCurrencies(): Flow<List<Currency>>

    @Insert
    suspend fun insertCurrencies(currencies: List<Currency>)

    @Query("DELETE FROM currency_table")
    suspend fun removeAllCurrencies()
}