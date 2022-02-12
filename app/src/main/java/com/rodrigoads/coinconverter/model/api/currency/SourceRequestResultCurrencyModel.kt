package com.rodrigoads.coinconverter.model.api.currency

import com.google.gson.annotations.SerializedName

data class SourceRequestResultCurrencyModel(
    @SerializedName("currencies")
    val resultsCurrencies: LinkedHashMap<String, SourceRequestCurrencyItemModel>,
) {}
