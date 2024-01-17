package com.msa.supervisor.view.adapter.holder.report

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.msa.supervisor.databinding.ItemOrderStatusReportBinding
import com.msa.supervisor.model.data.response.report.DealersItemModel
import com.msa.supervisor.model.data.response.report.OrderStatusReportModel
import com.msa.supervisor.tool.Currency
import com.msa.supervisor.view.adapter.recycler.report.DealersItemReportAdapter


class OrderStatusReportHolder(
    private val binding: ItemOrderStatusReportBinding,
    val items: List<OrderStatusReportModel>,
    val context: Context
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: OrderStatusReportModel, position: Int) {
        if (position == 0)
            binding.horizontalScrollHeader.visibility = View.VISIBLE
        else
            binding.horizontalScrollHeader.visibility = View.GONE

        if (position + 1 == items.size)
            binding.horizontalScrollFooter.visibility = View.VISIBLE
        else
            binding.horizontalScrollFooter.visibility = View.GONE

        binding.txtNumberItem.text = position.toString()
        binding.txtDate.text = item.date
        binding.txtOrderWeight.text = Currency(item.orderWeight).toFormattedString()
        binding.txtInProgressOrder.text = Currency(item.inProgressOrderWeight).toFormattedString()
        binding.txtFinalWeight.text = Currency(item.finalWeight).toFormattedString()

        var sum_orderWeight = Currency.ZERO
        var sum_inProgressOrderWeight = Currency.ZERO
        var sum_finalWeight = Currency.ZERO

        items.forEach {
            sum_orderWeight = sum_orderWeight.add(it.orderWeight)
            sum_inProgressOrderWeight = sum_inProgressOrderWeight.add(it.inProgressOrderWeight)
            sum_finalWeight = sum_finalWeight.add(it.finalWeight)
        }
        binding.txtFOrderWeight.text = sum_orderWeight.toFormattedString()
        binding.txtFoFinalWeight.text = sum_finalWeight.toFormattedString()
        binding.txtFInProgressOrder.text =
            sum_inProgressOrderWeight.toFormattedString()

        val childView: View = binding.horizontalScrollView.getChildAt(0)
        childView.setOnClickListener {
            Log.e(TAG, "OrderStatusReportHolderbind: ")
            setexpandRounting(!binding.expandDealersItem.isExpanded)
            item.dealersItems?.let { it1 -> showDealersItemRreport(items = it1) }
        }
    }

    private fun showDealersItemRreport(items: List<DealersItemModel>) {
        val adapter = DealersItemReportAdapter(items,context)
        val layoutManagerFlexbox = FlexboxLayoutManager(context)
        layoutManagerFlexbox.setFlexDirection(FlexDirection.ROW_REVERSE)
        layoutManagerFlexbox.setJustifyContent(JustifyContent.FLEX_START)

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