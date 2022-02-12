package com.rodrigoads.coinconverter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoads.coinconverter.R
import com.rodrigoads.coinconverter.databinding.CurrencyItemListBinding
import com.rodrigoads.coinconverter.database.entities.Currency
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

class CurrenciesAdapter(
    private val list: List<Currency>,
    private val context: Context,
    private val onClick: (Currency) -> Unit
) : RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            CurrencyItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding, context, onClick)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CurrencyViewHolder(
        private val currencyItem: CurrencyItemListBinding,
        private val context: Context,
        private val onClick: (Currency) -> Unit
    ) : RecyclerView.ViewHolder(currencyItem.root) {
        fun bind(currency: Currency) {

            val locale = Locale("pt", "BR")
            val numberFormat = NumberFormat.getCurrencyInstance(locale)

            val decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale)
            val decimalFormat = DecimalFormat("#0.00#", decimalFormatSymbols)

            currencyItem.textViewTerm.text = currency.currencyTerm
            currencyItem.textViewValue.text = numberFormat.format(currency.currencyBuy)
            (decimalFormat.format(currency.currencyVariation) + context.getString(R.string.percentage))
                .also { currencyItem.textViewPctInformation.text = it }

            (context.getString(R.string.conversion_from) + " " + currency.currencyTerm)
                .also { currencyItem.textViewConversionFrom.text = it }


            if (currency.currencyVariation <= 0.0) {
                currencyItem.textViewPctInformation.setTextColor(
                    ContextCompat
                        .getColor(context, R.color.main_red)
                )
            } else {
                currencyItem.textViewPctInformation.setTextColor(
                    ContextCompat
                        .getColor(context, R.color.main_green)
                )
            }

            currencyItem.buttonCustomConversion.setOnClickListener {
                onClick(currency)
            }
        }
    }
}