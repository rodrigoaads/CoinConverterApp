package com.rodrigoads.coinconverter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rodrigoads.coinconverter.database.entities.Stock
import com.rodrigoads.coinconverter.model.api.stock.SourceRequestStockItemModel
import com.rodrigoads.coinconverter.model.api.stock.SourceRequestStockModel
import com.rodrigoads.coinconverter.repositories.StockApiResponseRepository
import com.rodrigoads.coinconverter.repositories.StockRepository
import com.rodrigoads.coinconverter.repositories.sealed.Result
import kotlinx.coroutines.launch
import java.lang.Exception

class StockViewModel(
    private val apiResponseRepository: StockApiResponseRepository,
    private val stockRepository: StockRepository
) : ViewModel() {
    val stockList = stockRepository.getStocks.asLiveData()
    val requestErrorMessage = MutableLiveData<String>()
    val stocksFormatErrorMessage = MutableLiveData<String>()

    val stocksIsInitialized = MutableLiveData(false)

    private val requestStockFields = "stocks"

    fun getStocks() {
        viewModelScope.launch {

            val retrofitRequest = try {
                apiResponseRepository.getStocks(requestStockFields)
            } catch (e: Exception) {
                Result.Error(Exception(e))
            }

            when (retrofitRequest) {
                is Result.Success<SourceRequestStockModel?> -> {
                    val request = retrofitRequest.data

                    if (request?.sourceResultStocks?.resultsStocks != null && request.sourceValidKey) {
                        getStocksList(request.sourceResultStocks.resultsStocks)
                    } else {
                        stocksFormatErrorMessage.postValue("Erro ao receber atualização :(")
                        if (stocksIsInitialized.value != true) stocksIsInitialized.postValue(true)
                    }
                }
                else -> {
                    requestErrorMessage.postValue("Algo em nossa conexão deu errado :(")
                    if (stocksIsInitialized.value != true) stocksIsInitialized.postValue(true)
                }
            }
        }
    }

    private fun getStocksList(apiResponse: LinkedHashMap<String, SourceRequestStockItemModel>) {
        viewModelScope.launch {
            val stockList: MutableList<Stock> = mutableListOf()
            apiResponse.forEach {
                stockList.add(
                    Stock(
                        it.key,
                        it.value.requestStockName,
                        it.value.requestStockLocation,
                        it.value.requestStockPoints,
                        it.value.requestStockVariation
                    )
                )
            }
            stockRepository.removeAllStocks()
            stockRepository.insertStocks(stockList)
            if (stocksIsInitialized.value != true) stocksIsInitialized.postValue(true)
        }
    }
}