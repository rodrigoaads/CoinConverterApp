package com.rodrigoads.coinconverter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rodrigoads.coinconverter.database.entities.Bitcoin
import com.rodrigoads.coinconverter.model.api.bitcoin.SourceRequestBitcoinItemModel
import com.rodrigoads.coinconverter.model.api.bitcoin.SourceRequestBitcoinModel
import com.rodrigoads.coinconverter.repositories.BitcoinApiResponseRepository
import com.rodrigoads.coinconverter.repositories.BitcoinRepository
import com.rodrigoads.coinconverter.repositories.sealed.Result
import kotlinx.coroutines.launch
import java.lang.Exception

class BitcoinViewModel(
    private val apiResponseRepository: BitcoinApiResponseRepository,
    private val bitcoinRepository: BitcoinRepository
) : ViewModel() {
    val bitcoinList = bitcoinRepository.getBitcoin.asLiveData()
    val requestErrorMessage = MutableLiveData<String>()
    val bitcoinFormatErrorMessage = MutableLiveData<String>()

    val bitcoinIsInitialized = MutableLiveData(false)

    private val requestBitcoinFields = "bitcoin"

    fun getBitcoin() {
        viewModelScope.launch {

            val retrofitRequest = try {
                apiResponseRepository.getBitcoin(requestBitcoinFields)
            } catch (e: Exception) {
                Result.Error(Exception(e))
            }

            when (retrofitRequest) {
                is Result.Success<SourceRequestBitcoinModel?> -> {
                    val request = retrofitRequest.data

                    if (request?.sourceResultBitcoin?.resultsBitcoin != null && request.sourceValidKey) {
                        getBitcoinList(request.sourceResultBitcoin.resultsBitcoin)
                    } else {
                        bitcoinFormatErrorMessage.postValue("Erro ao receber atualização :(")
                        if (bitcoinIsInitialized.value != true) bitcoinIsInitialized.postValue(true)
                    }
                }
                else -> {
                    requestErrorMessage.postValue("Algo em nossa conexão deu errado :(")
                    if (bitcoinIsInitialized.value != true) bitcoinIsInitialized.postValue(true)
                }
            }
        }
    }

    private fun getBitcoinList(apiResponse: LinkedHashMap<String, SourceRequestBitcoinItemModel>) {
        viewModelScope.launch {
            val bitcoinList: MutableList<Bitcoin> = mutableListOf()
            apiResponse.forEach {
                bitcoinList.add(
                    Bitcoin(
                        it.key,
                        it.value.requestBitcoinBrokerName,
                        it.value.requestBitcoinFormat[0],
                        it.value.requestBitcoinFormat[1],
                        it.value.requestBitcoinBrokerLast,
                        it.value.requestBitcoinBrokerBuy,
                        it.value.requestBitcoinBrokerSell,
                        it.value.requestBitcoinBrokerVariation
                    )
                )
            }
            bitcoinRepository.removerAllBitcoin()
            bitcoinRepository.insertBitcoin(bitcoinList)
            if (bitcoinIsInitialized.value != true) bitcoinIsInitialized.postValue(true)
        }
    }
}