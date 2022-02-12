package com.rodrigoads.coinconverter.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodrigoads.coinconverter.database.entities.Stock
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Query("SELECT * FROM stocks_table")
    fun getStocks(): Flow<List<Stock>>

    @Insert
    suspend fun insertStocks(stocks: List<Stock>)

    @Query("DELETE FROM stocks_table")
    suspend fun removeAllStocks()
}