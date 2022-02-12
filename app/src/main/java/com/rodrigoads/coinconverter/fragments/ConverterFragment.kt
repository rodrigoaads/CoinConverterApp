package com.rodrigoads.coinconverter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rodrigoads.coinconverter.R
import com.rodrigoads.coinconverter.databinding.FragmentConverterBinding
import com.rodrigoads.coinconverter.database.entities.Currency
import com.rodrigoads.coinconverter.viewmodel.ConverterViewModel
import java.lang.Exception
import java.text.NumberFormat
import java.util.*

class ConverterFragment : Fragment() {
    private lateinit var binding: FragmentConverterBinding

    private lateinit var converterViewModel: ConverterViewModel

    private val locale = Locale("pt", "BR")
    private val numberFormat = NumberFormat.getCurrencyInstance(locale)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        converterViewModel =
            ViewModelProvider(requireActivity()).get(ConverterViewModel::class.java)
        converterViewModel.setCurrencyStartValue()

        binding = FragmentConverterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textInputEditTextValueConverter.setText("1")
        getValue()
    }

    override fun onStart() {
        super.onStart()
        converterViewModel.getCurrency.observe(this, Observer {
            setCurrencyInfos(it)
        })

        converterViewModel.resultTextViewConverter.observe(this, Observer {
            binding.textViewResultValueConverter.text = numberFormat.format(it.toDouble())
        })
    }

    private fun getValue() {
        binding.textInputEditTextValueConverter.doAfterTextChanged { text ->
            try {
                if (text.toString().isNotEmpty()) {
                    converterViewModel.calculateConversion(text.toString().toDouble())
                } else {
                    getString(R.string.sign).also { binding.textViewResultValueConverter.text = it }
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    getString(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setCurrencyInfos(currency: Currency) {
        binding.textViewTermConverter.text = currency.currencyTerm
        binding.textViewUnitaryValueConverter.text = numberFormat.format(currency.currencyBuy)
    }
}