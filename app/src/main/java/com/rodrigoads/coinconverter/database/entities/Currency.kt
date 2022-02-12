package com.rodrigoads.coinconverter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class Currency(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "currency_term")
    val currencyTerm: String,

    @ColumnInfo(name = "currency_name")
    val currencyName: String,

    @ColumnInfo(name = "currency_buy")
    val currencyBuy: Double,

    @ColumnInfo(name = "currency_sell")
    val currencySell: Double,

    @ColumnInfo(name = "currency_variation")
    val currencyVariation: Double
) {}
