package com.msa.supervisor.view.adapter.holder.report

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemReturnReasonHolderBinding
import com.msa.supervisor.model.data.response.report.ReturnReasonModel
import com.msa.supervisor.tool.Currency

class ReturnReasonHolder(
    val binding: ItemReturnReasonHolderBinding,
    private val items: List<ReturnReasonModel>,
    val context: Context
):RecyclerView.ViewHolder(binding.root){


    fun bind(item: ReturnReasonModel, position: Int){
        if (position == 0)
            binding.horizontalScrollHeader.visibility = View.VISIBLE
        else
            binding.horizontalScrollHeader.visibility = View.GONE


        if (position + 1 == items.size)
            binding.horizontalScrollFooter.visibility = View.VISIBLE

        binding.txtNumberItem.text = item.reasonCode
        binding.txtNameReasonReturn.text = item.reason
        binding.txtNumberOfReturn.text = Currency(item.productCountCa).toFormattedString()


        var sumProductCountCa = Currency.ZERO
        items.forEach {
            sumProductCountCa = sumProductCountCa.add(it.productCountCa)
        }
        binding.txtFNumberOfReturn.text = sumProductCountCa.toFormattedString()

    }
}