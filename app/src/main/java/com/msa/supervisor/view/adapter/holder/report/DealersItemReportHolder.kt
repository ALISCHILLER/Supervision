package com.msa.supervisor.view.adapter.holder.report

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.msa.supervisor.databinding.ItemDealersItemReportBinding
import com.msa.supervisor.model.data.response.report.CustomerItemModel
import com.msa.supervisor.model.data.response.report.DealersItemModel
import com.msa.supervisor.tool.Currency
import com.msa.supervisor.view.adapter.recycler.report.CustomerItemReportAdapter

class DealersItemReportHolder(
    private val binding: ItemDealersItemReportBinding,
    private val items: List<DealersItemModel>,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DealersItemModel, position: Int) {
        if (position == 0)
            binding.horizontalScrollHeader.visibility = View.VISIBLE
        else
            binding.horizontalScrollHeader.visibility = View.GONE

        if (position + 1 == items.size)
            binding.horizontalScrollFooter.visibility = View.VISIBLE
        binding.txtNumberItem.text = item.dealerCode
        binding.txtNameVisitor.text = item.dealerName
        binding.txtOrderWeight.text = Currency(item.orderWeight).toFormattedString()
        binding.txtInProgressOrder.text = Currency(item.inProgressOrderWeight).toFormattedString()
        binding.txtFinalWeight.text = Currency(item.deliverdOrderWeight).toFormattedString()


        val childView: View = binding.horizontalScrollView.getChildAt(0)
        childView.setOnClickListener {
            item.customerItemModels?.let { it1 ->
                showCustomerItemRreport(items = it1)
            }
            Log.e(ContentValues.TAG, "customerItemModels: $it ")
            setexpandRounting(!binding.expandDealersItem.isExpanded)
        }

        var sum_orderWeight = Currency.ZERO
        var sum_inProgressOrderWeight = Currency.ZERO
        var sum_finalWeight = Currency.ZERO
        items.forEach {
            sum_orderWeight = sum_orderWeight.add(it.orderWeight)
            sum_inProgressOrderWeight = sum_inProgressOrderWeight.add(it.inProgressOrderWeight)
            sum_finalWeight= sum_finalWeight.add(it.deliverdOrderWeight)
        }
        binding.txtFOrderWeight.text = sum_finalWeight.toFormattedString()
        binding.txtFFinalWeight.text = sum_orderWeight.toFormattedString()
        binding.txtFInFProgressOrder.text = sum_inProgressOrderWeight.toFormattedString()

    }


    private fun showCustomerItemRreport(items: List<CustomerItemModel>) {
        val adapter = CustomerItemReportAdapter(items)
        val layoutManagerVertical = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerDealersItems.layoutManager = layoutManagerVertical
        binding.recyclerDealersItems.adapter = adapter
    }

    private fun setexpandRounting(cheak: Boolean) {
        if (cheak) {
            binding.expandDealersItem.expand()
            binding.expandDealersItem.visibility=View.VISIBLE
        } else {
            binding.expandDealersItem.collapse()
        }
    }
}