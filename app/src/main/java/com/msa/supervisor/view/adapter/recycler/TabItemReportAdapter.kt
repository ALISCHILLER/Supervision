package com.msa.supervisor.view.adapter.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemTapReportBinding
import com.msa.supervisor.model.data.response.report.ItemReportModel
import com.msa.supervisor.view.adapter.holder.TabItemReportHolder
/**
 * create by Ali Soleymani.
 */
class TabItemReportAdapter(
    private val items: MutableList<ItemReportModel>,
    private val click: TabItemReportHolder.Click,
    private val context: Context
):RecyclerView.Adapter<TabItemReportHolder>() {


    var selectPosition: Int = 0


    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabItemReportHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return TabItemReportHolder(
            ItemTapReportBinding.inflate(layoutInflater!!,parent,false),click,context)
    }

    override fun getItemCount()=items.size

    override fun onBindViewHolder(holder: TabItemReportHolder, position: Int) {
        holder.bind(items[position],position,selectPosition)
    }

}