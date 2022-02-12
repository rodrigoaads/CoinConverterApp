package com.rodrigoads.coinconverter.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodrigoads.coinconverter.repositories.BitcoinApiResponseRepository
import com.rodrigoads.coinconverter.repositories.BitcoinRepository
import com.rodrigoads.coinconverter.viewmodel.BitcoinViewModel

class BitcoinViewModelFactory(
    private val apiResponseRepository: BitcoinApiResponseRepository,
    private val bitcoinRepository: BitcoinRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BitcoinViewModel::class.java)) {
            return BitcoinViewModel(apiResponseRepository, bitcoinRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}