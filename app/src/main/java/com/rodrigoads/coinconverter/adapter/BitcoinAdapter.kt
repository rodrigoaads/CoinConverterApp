package com.rodrigoads.coinconverter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoads.coinconverter.R
import com.rodrigoads.coinconverter.databinding.BitCoinItemListBinding
import com.rodrigoads.coinconverter.database.entities.Bitcoin
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

class BitcoinAdapter(
    private val list: List<Bitcoin>,
    private val context: Context
) : RecyclerView.Adapter<BitcoinAdapter.BitcoinViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitcoinViewHolder {
        val binding =
            BitCoinItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BitcoinViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: BitcoinViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class BitcoinViewHolder(
        private val bitCoinItem: BitCoinItemListBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(bitCoinItem.root) {
        fun bind(bitcoin: Bitcoin) {

            val locale = Locale(
                bitcoin.bitcoinLanguage.substring(0..1),
                bitcoin.bitcoinLanguage.substring(3..4)
            )
            val numberFormat = NumberFormat.getCurrencyInstance(locale)

            val variationFormatLocale = Locale("pt", "BR")
            val decimalFormatSymbols = DecimalFormatSymbols.getInstance(variationFormatLocale)
            val decimalFormat = DecimalFormat("#0.00#", decimalFormatSymbols)

            "${bitcoin.bitcoinName} / ${bitcoin.bitcoinISO}".also {
                bitCoinItem.textViewBitcoinName.text = it
            }
            bitCoinItem.textViewBitcoinVariation.text =
                decimalFormat.format(bitcoin.bitcoinVariation)
            bitCoinItem.textViewBitcoinValue.text = numberFormat.format(bitcoin.bitcoinLast)

            if (bitcoin.bitcoinVariation <= 0.0) {
                bitCoinItem.textViewBitcoinVariation.setTextColor(
                    ContextCompat
                        .getColor(context, R.color.main_red)
                )
            } else {
                bitCoinItem.textViewBitcoinVariation.setTextColor(
                    ContextCompat
                        .getColor(context, R.color.main_green)
                )
            }

        }
    }
}