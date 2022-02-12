package com.rodrigoads.coinconverter.viewmodel

import androidx.lifecycle.*
import com.rodrigoads.coinconverter.database.entities.Currency
import com.rodrigoads.coinconverter.model.api.currency.SourceRequestCurrencyItemModel
import com.rodrigoads.coinconverter.model.api.currency.SourceRequestCurrencyModel
import com.rodrigoads.coinconverter.repositories.CurrencyRepository
import com.rodrigoads.coinconverter.repositories.CurrencyApiResponseRepository
import com.rodrigoads.coinconverter.repositories.sealed.Result
import kotlinx.coroutines.launch
import java.lang.Exception

class CurrenciesViewModel(
    private val apiResponseRepository: CurrencyApiResponseRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    val currencyList = currencyRepository.getCurrencies.asLiveData()
    val requestErrorMessage = MutableLiveData<String>()
    val currenciesFormatErrorMessage = MutableLiveData<String>()

    val currenciesIsInitialized = MutableLiveData(false)

    private val requestCurrenciesFields = "USD,EUR,GBP,CAD,AUD"

    fun getCurrencies() {
        viewModelScope.launch {

            val retrofitRequest = try {
                apiResponseRepository.getCurrencies(requestCurrenciesFields)
            } catch (e: Exception) {
                Result.Error(Exception(e))
            }

            when (retrofitRequest) {
                is Result.Success<SourceRequestCurrencyModel?> -> {
                    val request = retrofitRequest.data

                    if (request?.sourceResultCurrency?.resultsCurrencies != null && request.sourceValidKey) {
                        getCurrenciesList(request.sourceResultCurrency.resultsCurrencies)
                    } else {
                        currenciesFormatErrorMessage.postValue("Erro ao receber atualização :(")
                        if (currenciesIsInitialized.value != true) currenciesIsInitialized.postValue(
                            true
                        )
                    }
                }
                else -> {
                    requestErrorMessage.postValue("Algo em nossa conexão deu errado :(")
                    if (currenciesIsInitialized.value != true) currenciesIsInitialized.postValue(
                        true
                    )
                }
            }

        }
    }

    private fun getCurrenciesList(apiResponse: LinkedHashMap<String, SourceRequestCurrencyItemModel>) {
        viewModelScope.launch {
            val currencyList: MutableList<Currency> = mutableListOf()
            apiResponse.forEach {
                currencyList.add(
                    Currency(
                        it.key,
                        it.value.requestCurrencyName,
                        it.value.requestCurrencyBuy,
                        it.value.requestCurrencySell,
                        it.value.requestCurrencyVariation
                    )
                )
            }
            currencyRepository.removeAllCurrencies()
            currencyRepository.insertCurrencies(currencyList)
            if (currenciesIsInitialized.value != true) currenciesIsInitialized.postValue(true)
        }
    }
}