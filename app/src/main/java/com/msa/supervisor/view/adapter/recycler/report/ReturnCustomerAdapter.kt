package com.msa.supervisor.view.adapter.recycler.report

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemReturnCustomerHolderBinding
import com.msa.supervisor.databinding.ItemReturnReportHolderBinding
import com.msa.supervisor.model.data.response.report.ReturnCustomerModel
import com.msa.supervisor.model.data.response.report.ReturnDealerModel
import com.msa.supervisor.view.adapter.holder.report.ReturnCustomerHolder
import com.msa.supervisor.view.adapter.holder.report.ReturnReportHolder
/**
 * create by Ali Soleymani.
 */
class ReturnCustomerAdapter(
     val items: List<ReturnCustomerModel>,
    val context: Context
):RecyclerView.Adapter<ReturnCustomerHolder>(){

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ReturnCustomerHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return ReturnCustomerHolder(
            ItemReturnCustomerHolderBinding.inflate(layoutInflater!!, parent, false),
            items, context)
    }

    override fun getItemCount()= items.size

    override fun onBindViewHolder(holder: ReturnCustomerHolder, position: Int)  {
        holder.bind(items[position], position)
    }
}