package com.rodrigoads.coinconverter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoads.coinconverter.R
import com.rodrigoads.coinconverter.databinding.StockItemListBinding
import com.rodrigoads.coinconverter.database.entities.Stock
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class StockAdapter(
    private val list: List<Stock>,
    private val context: Context
) : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding =
            StockItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class StockViewHolder(
        private val stockItem: StockItemListBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(stockItem.root) {
        fun bind(stock: Stock) {
            val locale = Locale("pt", "BR")

            val decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale)
            val decimalFormat = DecimalFormat("#0.00#", decimalFormatSymbols)

            "${stock.stockTerm} / ${stock.stockName}".also { stockItem.textViewStockName.text = it }
            stockItem.textViewStockLocation.text = stock.stockLocation
            stockItem.textViewStockVariation.text = decimalFormat.format(stock.stockVariation)

            if (stock.stockVariation <= 0.0) {
                stockItem.imageViewStockVariation.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down)
                stockItem.textViewStockVariation.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.main_red
                    )
                )
            } else {
                stockItem.imageViewStockVariation.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up)
                stockItem.textViewStockVariation.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.main_green
                    )
                )
            }
        }
    }
}