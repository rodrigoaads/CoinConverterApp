package com.rodrigoads.coinconverter.model.api.bitcoin

import com.google.gson.annotations.SerializedName

data class SourceRequestBitcoinItemModel(
    @SerializedName("name")
    val requestBitcoinBrokerName: String,

    @SerializedName("format")
    val requestBitcoinFormat: ArrayList<String>,

    @SerializedName("last")
    val requestBitcoinBrokerLast: Double,

    @SerializedName("buy")
    val requestBitcoinBrokerBuy: Double,

    @SerializedName("sell")
    val requestBitcoinBrokerSell: Double,

    @SerializedName("variation")
    val requestBitcoinBrokerVariation: Double
) {}
