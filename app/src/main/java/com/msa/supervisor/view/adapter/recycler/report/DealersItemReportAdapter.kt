package com.msa.supervisor.view.adapter.recycler.report

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemDealersItemReportBinding
import com.msa.supervisor.databinding.ItemOrderStatusReportBinding
import com.msa.supervisor.model.data.response.report.DealersItemModel
import com.msa.supervisor.view.adapter.holder.report.DealersItemReportHolder
import com.msa.supervisor.view.adapter.holder.report.OrderStatusReportHolder
/**
 * create by Ali Soleymani.
 */
class DealersItemReportAdapter(
    private val items:List<DealersItemModel>,
    val context: Context
): RecyclerView.Adapter<DealersItemReportHolder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DealersItemReportHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return DealersItemReportHolder(
            ItemDealersItemReportBinding.inflate(layoutInflater!!,parent,false),items,context)
    }

    override fun getItemCount()=items.size

    override fun onBindViewHolder(holder: DealersItemReportHolder, position: Int) {
        holder.bind(items[position],position)
    }
}