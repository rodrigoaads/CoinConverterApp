package com.rodrigoads.coinconverter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigoads.coinconverter.adapter.StockAdapter
import com.rodrigoads.coinconverter.api.ApiService
import com.rodrigoads.coinconverter.application.CoinConverterApplication
import com.rodrigoads.coinconverter.databinding.FragmentStocksBinding
import com.rodrigoads.coinconverter.database.entities.Stock
import com.rodrigoads.coinconverter.repositories.StockApiResponseRepository
import com.rodrigoads.coinconverter.viewmodel.StockViewModel
import com.rodrigoads.coinconverter.viewmodel.factory.StockViewModelFactory

class StocksFragment : Fragment() {
    private lateinit var binding: FragmentStocksBinding

    private lateinit var stockViewModel: StockViewModel
    private val retrofitService = ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stockViewModel = ViewModelProvider(
            requireActivity(),
            StockViewModelFactory(
                StockApiResponseRepository(retrofitService),
                (requireActivity().application as CoinConverterApplication).stockRepository
            )
        ).get(StockViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStocksBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        stockViewModel.stocksIsInitialized.observe(this, Observer {
            if (it) {
                binding.progressBarStocks.visibility = View.GONE
            }
        })

        stockViewModel.stockList.observe(this, Observer {
            initStocksRecyclerView(it)
        })

        stockViewModel.requestErrorMessage.observe(this, Observer {
            if (stockViewModel.stocksIsInitialized.value != true) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })

        stockViewModel.stocksFormatErrorMessage.observe(this, Observer {
            if (stockViewModel.stocksIsInitialized.value != true) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initStocksRecyclerView(list: List<Stock>) {
        binding.recyclerViewStocks.apply {
            adapter = StockAdapter(list, context)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onResume() {
        super.onResume()
        if (stockViewModel.stocksIsInitialized.value != true) {
            stockViewModel.getStocks()
        }
    }
}