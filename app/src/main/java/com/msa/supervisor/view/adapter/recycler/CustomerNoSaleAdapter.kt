package com.msa.supervisor.view.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomerNosaleBinding
import com.msa.supervisor.databinding.ItemInvoiceBalanceReportBinding
import com.msa.supervisor.model.data.response.report.CustomerNoSaleReportModel
import com.msa.supervisor.view.adapter.holder.InvoiveBalanceHolder
import com.msa.supervisor.view.adapter.holder.customerNoSaleHolder
/**
 * create by Ali Soleymani.
 */
class CustomerNoSaleAdapter(
    private val items: MutableList<CustomerNoSaleReportModel>,
): RecyclerView.Adapter<customerNoSaleHolder>() {


    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): customerNoSaleHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return customerNoSaleHolder(
            ItemCustomerNosaleBinding.inflate(layoutInflater!!,parent,false))
    }

    override fun getItemCount(): Int=items.size
    override fun onBindViewHolder(holder: customerNoSaleHolder, position: Int) {
        holder.bind(items[position])
    }

}