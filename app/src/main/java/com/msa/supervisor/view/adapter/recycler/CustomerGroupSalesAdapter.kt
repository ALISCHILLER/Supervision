package com.msa.supervisor.view.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomerGroupSalesReportBinding
import com.msa.supervisor.databinding.ItemInvoiceBalanceReportBinding
import com.msa.supervisor.model.data.response.report.CustomerSalesSummaryReportModel
import com.msa.supervisor.view.adapter.holder.CustomerGroupSalesHolder
import com.msa.supervisor.view.adapter.holder.InvoiveBalanceHolder

/**
 * create by Ali Soleymani.
 */
class CustomerGroupSalesAdapter (
    private val items: MutableList<CustomerSalesSummaryReportModel>,
): RecyclerView.Adapter<CustomerGroupSalesHolder>() {


    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerGroupSalesHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return CustomerGroupSalesHolder(
            ItemCustomerGroupSalesReportBinding.inflate(layoutInflater!!,parent,false))
    }
    override fun getItemCount()=items.size
    override fun onBindViewHolder(holder: CustomerGroupSalesHolder, position: Int) {
        holder.bind(items[position])
    }
}