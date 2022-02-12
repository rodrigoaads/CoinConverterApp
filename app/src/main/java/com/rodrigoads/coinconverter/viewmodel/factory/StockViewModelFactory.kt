package com.rodrigoads.coinconverter.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodrigoads.coinconverter.repositories.StockApiResponseRepository
import com.rodrigoads.coinconverter.repositories.StockRepository
import com.rodrigoads.coinconverter.viewmodel.StockViewModel

class StockViewModelFactory(
    private val apiResponseRepository: StockApiResponseRepository,
    private val stockRepository: StockRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            return StockViewModel(apiResponseRepository, stockRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}