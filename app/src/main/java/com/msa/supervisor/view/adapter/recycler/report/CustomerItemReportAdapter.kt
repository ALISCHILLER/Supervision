package com.msa.supervisor.view.adapter.recycler.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomerItemReportBinding
import com.msa.supervisor.databinding.ItemDealersItemReportBinding
import com.msa.supervisor.model.data.response.report.CustomerItemModel
import com.msa.supervisor.view.adapter.holder.report.CustomerItemReportHolder
import com.msa.supervisor.view.adapter.holder.report.DealersItemReportHolder
/**
 * create by Ali Soleymani.
 */
class CustomerItemReportAdapter(
    private val items:List<CustomerItemModel>
): RecyclerView.Adapter<CustomerItemReportHolder>()  {

    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomerItemReportHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return CustomerItemReportHolder(
            ItemCustomerItemReportBinding.inflate(layoutInflater!!,parent,false),items)
    }

    override fun getItemCount()=items.size

    override fun onBindViewHolder(holder: CustomerItemReportHolder, position: Int) {
        holder.bind(items[position],position)
    }


}