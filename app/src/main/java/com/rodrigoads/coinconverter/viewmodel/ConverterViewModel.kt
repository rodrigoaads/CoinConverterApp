package com.rodrigoads.coinconverter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoads.coinconverter.database.entities.Currency
import java.text.DecimalFormat

class ConverterViewModel() : ViewModel() {
    val getCurrency = MutableLiveData<Currency>()
    val resultTextViewConverter = MutableLiveData<String>()

    private val decimalFormat = DecimalFormat("0.00")

    fun setCurrency(currency: Currency) {
        getCurrency.postValue(currency)
    }

    fun calculateConversion(value: Double) {
        val result =
            value * (decimalFormat.format(getCurrency.value?.currencyBuy).toDouble() ?: 0.0)
        resultTextViewConverter.postValue(result.toString())
    }

    fun setCurrencyStartValue() {
        resultTextViewConverter.postValue(getCurrency.value?.currencyBuy.toString() ?: "0.0")
    }
}