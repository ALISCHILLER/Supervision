package com.msa.supervisor.view.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemOrederConfirmBinding
import com.msa.supervisor.databinding.ItemPinCodeBinding
import com.msa.supervisor.model.data.response.customer.CustomerPinModel
import com.msa.supervisor.model.data.response.order.OrderConfirmModel
import com.msa.supervisor.view.adapter.holder.ListPinCodeHolder
import com.msa.supervisor.view.adapter.holder.OrderConfirmHolder
import java.util.Locale
/**
 * create by Ali Soleymani.
 */
class ListPinCodeAdapter(
    private var items: List<CustomerPinModel>,
    private var itemsFull: List<CustomerPinModel>,
) : RecyclerView.Adapter<ListPinCodeHolder>(), Filterable {


    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPinCodeHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return ListPinCodeHolder(
            ItemPinCodeBinding.inflate(layoutInflater!!, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ListPinCodeHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = mutableListOf<CustomerPinModel>()
                val searchQuery = constraint.toString().toLowerCase(Locale.getDefault()).trim()

                if (searchQuery.isEmpty()) {
                    filteredResults.addAll(itemsFull)
                } else {
                    for (customerPin in itemsFull) {
                        if (customerPin.uniqueId.toString().lowercase(Locale.getDefault())
                                .contains(searchQuery) ||
                            customerPin.customerUniqueId.toString().lowercase(Locale.getDefault())
                                .contains(searchQuery) ||
                            customerPin.customerCode?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.customerName?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.pinDate?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.pinPDate?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.dealerName?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.pin1?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.pin2?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.pin3?.lowercase(Locale.getDefault())
                                ?.contains(searchQuery) == true ||
                            customerPin.pin4?.lowercase(Locale.getDefault())
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
                 items = results?.values as List<CustomerPinModel>
                notifyDataSetChanged()
            }

        }
    }


}