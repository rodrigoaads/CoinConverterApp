package com.rodrigoads.coinconverter.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodrigoads.coinconverter.database.entities.Bitcoin
import kotlinx.coroutines.flow.Flow

@Dao
interface BitcoinDao {
    @Query("SELECT * FROM bitcoin_table")
    fun getBitcoin(): Flow<List<Bitcoin>>

    @Insert
    suspend fun insertBitcoin(bitcoin: List<Bitcoin>)

    @Query("DELETE FROM bitcoin_table")
    suspend fun removeAllBitcoin()
}