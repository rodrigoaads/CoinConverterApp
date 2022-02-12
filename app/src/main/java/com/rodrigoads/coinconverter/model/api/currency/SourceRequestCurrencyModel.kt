package com.rodrigoads.coinconverter.model.api.currency

import com.google.gson.annotations.SerializedName

data class SourceRequestCurrencyModel(
    @SerializedName("by")
    val sourceBy: String,

    @SerializedName("valid_key")
    val sourceValidKey: Boolean,

    @SerializedName("results")
    val sourceResultCurrency: SourceRequestResultCurrencyModel,

    @SerializedName("execution_time")
    val sourceExecutionTime: Double,

    @SerializedName("from_cache")
    val from_cache: Boolean
) {}
