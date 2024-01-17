package com.msa.supervisor.view.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemInvoiceBalanceReportBinding
import com.msa.supervisor.databinding.ItemTapReportBinding

import com.msa.supervisor.model.data.response.report.ProductInvoiveBalanceReportModel
import com.msa.supervisor.view.adapter.holder.InvoiveBalanceHolder
import com.msa.supervisor.view.adapter.holder.TabItemReportHolder
/**
 * create by Ali Soleymani.
 */
class InvoiveBalanceAdapter(
    private val items: MutableList<ProductInvoiveBalanceReportModel>,
    private val click: InvoiveBalanceHolder.Click
):RecyclerView.Adapter<InvoiveBalanceHolder>() {

    private var layoutInflater: LayoutInflater? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiveBalanceHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return InvoiveBalanceHolder(
            ItemInvoiceBalanceReportBinding.inflate(layoutInflater!!,parent,false),click)
    }

    override fun getItemCount()=items.size

    override fun onBindViewHolder(holder: InvoiveBalanceHolder, position: Int) {
        holder.bind(items[position])
    }
}