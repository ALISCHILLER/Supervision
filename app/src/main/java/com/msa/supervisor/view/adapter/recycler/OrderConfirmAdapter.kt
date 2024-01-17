package com.msa.supervisor.view.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemInvoiceBalanceReportBinding
import com.msa.supervisor.databinding.ItemOrederConfirmBinding
import com.msa.supervisor.model.data.response.customer.CustomerPinModel
import com.msa.supervisor.model.data.response.order.OrderConfirmModel
import com.msa.supervisor.model.data.response.report.ProductInvoiveBalanceReportModel
import com.msa.supervisor.view.adapter.holder.InvoiveBalanceHolder
import com.msa.supervisor.view.adapter.holder.OrderConfirmHolder
import java.util.Locale
/**
 * create by Ali Soleymani.
 */
class OrderConfirmAdapter(
    private var items: List<OrderConfirmModel>,
    private var itemsFull: MutableList<OrderConfirmModel>,
    private val click: OrderConfirmHolder.Click
) : RecyclerView.Adapter<OrderConfirmHolder>(), Filterable {


    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderConfirmHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return OrderConfirmHolder(
            ItemOrederConfirmBinding.inflate(layoutInflater!!, parent, false), click
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: OrderConfirmHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = mutableListOf<OrderConfirmModel>()
                val searchQuery = constraint.toString().lowercase(Locale.getDefault()).trim()

                if (searchQuery.isEmpty()) {
                    filteredResults.addAll(itemsFull)
                } else {
                    for (customerPin in itemsFull) {
                        if (customerPin.dealerCode.toString().lowercase(Locale.getDefault())
                                .contains(searchQuery) ||
                            customerPin.customerCode?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.customerName?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.orderDate?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.orderStatus?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.dealerName?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.paymentType?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.comment?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.orderNumber?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true
                        ) {
                            filteredResults.add(customerPin)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredResults
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items = results?.values as List<OrderConfirmModel>
                notifyDataSetChanged()
            }

        }
    }


}