package com.msa.supervisor.view.adapter.holder.report

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.msa.supervisor.databinding.ItemReturnCustomerHolderBinding
import com.msa.supervisor.model.data.response.report.ReturnCustomerModel
import com.msa.supervisor.model.data.response.report.ReturnReasonModel
import com.msa.supervisor.tool.Currency
import com.msa.supervisor.view.adapter.recycler.report.ReturnReasonAdapter

class ReturnCustomerHolder(
    val binding:ItemReturnCustomerHolderBinding,
     val items: List<ReturnCustomerModel>,
    val context: Context
):RecyclerView.ViewHolder(binding.root){



    fun bind(item:ReturnCustomerModel, position: Int){
        if (position == 0)
            binding.horizontalScrollHeader.visibility = View.VISIBLE
        else
            binding.horizontalScrollHeader.visibility = View.GONE

        if (position + 1 == items.size)
            binding.horizontalScrollFooter.visibility = View.VISIBLE

        binding.txtNumberItem.text = item.customerCode
        binding.txtNameCustomer.text = item.customerName
        binding.txtNumberOfReturn.text = Currency(item.productCountCa).toFormattedString()

        var sumProductCountCa = Currency.ZERO
        items.forEach {
            sumProductCountCa = sumProductCountCa.add(it.productCountCa)
        }
        binding.txtFNumberOfReturn.text = sumProductCountCa.toFormattedString()



        val childView: View = binding.horizontalScrollView.getChildAt(0)
        childView.setOnClickListener {
            item.returnReasonModels?.let { it1 ->
                showCustomerItemRreport(items = it1)
            }
            Log.e(ContentValues.TAG, "customerItemModels: $it ")
            setexpandRounting(!binding.expandDealersItem.isExpanded)
        }


    }


    private fun showCustomerItemRreport(items: List<ReturnReasonModel>) {
        val adapter = ReturnReasonAdapter(items, context)
        val layoutManagerFlexbox = FlexboxLayoutManager(context)
        layoutManagerFlexbox.setFlexDirection(FlexDirection.ROW_REVERSE)
        layoutManagerFlexbox.setJustifyContent(JustifyContent.FLEX_START)
        binding.recyclerDealersItems.layoutManager = layoutManagerFlexbox
        binding.recyclerDealersItems.adapter = adapter
    }

    private fun setexpandRounting(cheak: Boolean) {
        if (cheak)
            binding.expandDealersItem.expand()
        else
            binding.expandDealersItem.collapse()
    }
}