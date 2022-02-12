package com.rodrigoads.coinconverter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stocks_table")
data class Stock(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "stock_term")
    val stockTerm: String,

    @ColumnInfo(name = "stock_name")
    val stockName: String,

    @ColumnInfo(name = "stock_location")
    val stockLocation: String,

    @ColumnInfo(name = "stock_points")
    val stockPoints: Double,

    @ColumnInfo(name = "stock_variation")
    val stockVariation: Double
) {}
