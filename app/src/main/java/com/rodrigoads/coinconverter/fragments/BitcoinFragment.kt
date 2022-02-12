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
import com.rodrigoads.coinconverter.adapter.BitcoinAdapter
import com.rodrigoads.coinconverter.api.ApiService
import com.rodrigoads.coinconverter.application.CoinConverterApplication
import com.rodrigoads.coinconverter.databinding.FragmentBitCoinBinding
import com.rodrigoads.coinconverter.database.entities.Bitcoin
import com.rodrigoads.coinconverter.repositories.BitcoinApiResponseRepository
import com.rodrigoads.coinconverter.viewmodel.BitcoinViewModel
import com.rodrigoads.coinconverter.viewmodel.factory.BitcoinViewModelFactory

class BitcoinFragment : Fragment() {
    private lateinit var binding: FragmentBitCoinBinding

    private lateinit var bitcoinViewModel: BitcoinViewModel
    private val retrofitService = ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bitcoinViewModel = ViewModelProvider(
            requireActivity(), BitcoinViewModelFactory(
                BitcoinApiResponseRepository(retrofitService),
                (requireActivity().application as CoinConverterApplication).bitcoinRepository
            )
        ).get(BitcoinViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBitCoinBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        bitcoinViewModel.bitcoinIsInitialized.observe(this, Observer {
            if (it) {
                binding.progressBarBitcoin.visibility = View.GONE
            }
        })

        bitcoinViewModel.bitcoinList.observe(this, Observer {
            initBitcoinRecyclerView(it)
        })

        bitcoinViewModel.requestErrorMessage.observe(this, Observer {
            if (bitcoinViewModel.bitcoinIsInitialized.value != true) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })

        bitcoinViewModel.bitcoinFormatErrorMessage.observe(this, Observer {
            if (bitcoinViewModel.bitcoinIsInitialized.value != true) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initBitcoinRecyclerView(list: List<Bitcoin>) {
        binding.recyclerViewBitcoin.apply {
            adapter = BitcoinAdapter(list, context)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onResume() {
        super.onResume()
        if (bitcoinViewModel.bitcoinIsInitialized.value != true) {
            bitcoinViewModel.getBitcoin()
        }
    }
}