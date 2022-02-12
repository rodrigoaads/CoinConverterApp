package com.rodrigoads.coinconverter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bitcoin_table")
data class Bitcoin(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "bitcoin_term")
    val bitcoinTerm: String,

    @ColumnInfo(name = "bitcoin_name")
    val bitcoinName: String,

    @ColumnInfo(name = "bitcoin_ISO")
    val bitcoinISO: String,

    @ColumnInfo(name = "bitcoin_language")
    val bitcoinLanguage: String,

    @ColumnInfo(name = "bitcoin_last")
    val bitcoinLast: Double,

    @ColumnInfo(name = "bitcoin_buy")
    val bitcoinBuy: Double,

    @ColumnInfo(name = "bitcoin_sell")
    val bitcoinSell: Double,

    @ColumnInfo(name = "bitcoin_variation")
    val bitcoinVariation: Double
) {}
