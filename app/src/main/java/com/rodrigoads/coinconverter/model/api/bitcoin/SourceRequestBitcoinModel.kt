package com.rodrigoads.coinconverter.model.api.bitcoin

import com.google.gson.annotations.SerializedName

data class SourceRequestBitcoinModel(
    @SerializedName("by")
    val sourceBy: String,

    @SerializedName("valid_key")
    val sourceValidKey: Boolean,

    @SerializedName("results")
    val sourceResultBitcoin: SourceRequestResultBitcoinModel,

    @SerializedName("execution_time")
    val sourceExecutionTime: Double,

    @SerializedName("from_cache")
    val from_cache: Boolean
) {}
