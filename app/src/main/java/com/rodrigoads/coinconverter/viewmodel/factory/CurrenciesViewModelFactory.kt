package com.rodrigoads.coinconverter.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodrigoads.coinconverter.repositories.CurrencyRepository
import com.rodrigoads.coinconverter.repositories.CurrencyApiResponseRepository
import com.rodrigoads.coinconverter.viewmodel.CurrenciesViewModel

class CurrenciesViewModelFactory(
    private val apiResponseRepository: CurrencyApiResponseRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrenciesViewModel::class.java)) {
            return CurrenciesViewModel(apiResponseRepository, currencyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}