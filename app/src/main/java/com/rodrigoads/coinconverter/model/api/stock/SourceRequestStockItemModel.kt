package com.rodrigoads.coinconverter.model.api.stock

import com.google.gson.annotations.SerializedName

data class SourceRequestStockItemModel(
    @SerializedName("name")
    val requestStockName: String,

    @SerializedName("location")
    val requestStockLocation: String,

    @SerializedName("points")
    val requestStockPoints: Double,

    @SerializedName("variation")
    val requestStockVariation: Double
) {}
