package com.rodrigoads.coinconverter.model.api.bitcoin

import com.google.gson.annotations.SerializedName

data class SourceRequestResultBitcoinModel(
    @SerializedName("bitcoin")
    val resultsBitcoin: LinkedHashMap<String, SourceRequestBitcoinItemModel>
) {}
