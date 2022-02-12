package com.rodrigoads.coinconverter.model.api.stock

import com.google.gson.annotations.SerializedName

data class SourceRequestStockModel(
    @SerializedName("by")
    val sourceBy: String,

    @SerializedName("valid_key")
    val sourceValidKey: Boolean,

    @SerializedName("results")
    val sourceResultStocks: SourceRequestResultStockModel,

    @SerializedName("execution_time")
    val sourceExecutionTime: Double,

    @SerializedName("from_cache")
    val from_cache: Boolean
) {}
