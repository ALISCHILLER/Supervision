package com.msa.supervisor.view.adapter.recycler.report

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemReturnReportHolderBinding
import com.msa.supervisor.model.data.response.report.ReturnDealerModel
import com.msa.supervisor.view.adapter.holder.report.ReturnReportHolder
/**
 * create by Ali Soleymani.
 */
class ReturnReportAdapter(
    private val items: MutableList<ReturnDealerModel>,
    val context: Context
) : RecyclerView.Adapter<ReturnReportHolder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ReturnReportHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return ReturnReportHolder(
            ItemReturnReportHolderBinding.inflate(layoutInflater!!, parent, false), items, context
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ReturnReportHolder, position: Int) {
        holder.bind(items[position], position)
    }
}