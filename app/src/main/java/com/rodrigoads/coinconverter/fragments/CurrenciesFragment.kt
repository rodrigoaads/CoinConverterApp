package com.rodrigoads.coinconverter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigoads.coinconverter.adapter.CurrenciesAdapter
import com.rodrigoads.coinconverter.api.ApiService
import com.rodrigoads.coinconverter.application.CoinConverterApplication
import com.rodrigoads.coinconverter.databinding.FragmentCurrenciesBinding
import com.rodrigoads.coinconverter.database.entities.Currency
import com.rodrigoads.coinconverter.repositories.CurrencyApiResponseRepository
import com.rodrigoads.coinconverter.viewmodel.ConverterViewModel
import com.rodrigoads.coinconverter.viewmodel.CurrenciesViewModel
import com.rodrigoads.coinconverter.viewmodel.factory.CurrenciesViewModelFactory

class CurrenciesFragment() : Fragment() {
    private lateinit var binding: FragmentCurrenciesBinding

    private lateinit var currenciesViewModel: CurrenciesViewModel
    private lateinit var converterViewModel: ConverterViewModel
    private val retrofitService = ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currenciesViewModel = ViewModelProvider(
            requireActivity(), CurrenciesViewModelFactory(
                CurrencyApiResponseRepository(retrofitService),
                (requireActivity().application as CoinConverterApplication).currencyRepository
            )
        ).get(CurrenciesViewModel::class.java)

        converterViewModel =
            ViewModelProvider(requireActivity()).get(ConverterViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrenciesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        currenciesViewModel.currenciesIsInitialized.observe(this, Observer {
            if (it) {
                binding.progressBarCurrencies.visibility = View.GONE
            }
        })

        currenciesViewModel.currencyList.observe(this, Observer {
            initCurrenciesRecyclerView(it)
        })

        currenciesViewModel.requestErrorMessage.observe(this, Observer {
            if (currenciesViewModel.currenciesIsInitialized.value != true) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })

        currenciesViewModel.currenciesFormatErrorMessage.observe(this, Observer {
            if (currenciesViewModel.currenciesIsInitialized.value != true) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initCurrenciesRecyclerView(list: List<Currency>) {
        binding.recyclerViewCoins.apply {
            adapter = CurrenciesAdapter(list, context) { getClickCustomConversion(it) }
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getClickCustomConversion(currency: Currency) {
        converterViewModel.setCurrency(currency)
        val action =
            CurrenciesFragmentDirections
                .actionCurrenciesFragmentToConverterFragment()
        this.findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        if (currenciesViewModel.currenciesIsInitialized.value != true) {
            currenciesViewModel.getCurrencies()
        }
    }
}