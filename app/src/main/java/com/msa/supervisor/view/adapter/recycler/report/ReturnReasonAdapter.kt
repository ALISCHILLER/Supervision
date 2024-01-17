package com.msa.supervisor.view.adapter.recycler.report

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemOrderStatusReportBinding
import com.msa.supervisor.databinding.ItemReturnReasonHolderBinding
import com.msa.supervisor.model.data.response.report.ReturnReasonModel
import com.msa.supervisor.view.adapter.holder.report.OrderStatusReportHolder
import com.msa.supervisor.view.adapter.holder.report.ReturnReasonHolder
/**
 * create by Ali Soleymani.
 */
class ReturnReasonAdapter(
    val items:List<ReturnReasonModel>,
    val context: Context
):RecyclerView.Adapter<ReturnReasonHolder>(){

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ReturnReasonHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return ReturnReasonHolder(
            ItemReturnReasonHolderBinding.inflate(layoutInflater!!,parent,false),items
            , context)
    }

    override fun getItemCount()=items.size

    override fun onBindViewHolder(holder: ReturnReasonHolder, position: Int) {
        holder.bind(items[position],position)
    }
}