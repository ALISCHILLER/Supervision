package com.msa.supervisor.view.adapter.holder.report
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomerItemReportBinding
import com.msa.supervisor.model.data.response.report.CustomerItemModel
import com.msa.supervisor.tool.Currency

class CustomerItemReportHolder(
    val binding: ItemCustomerItemReportBinding,
    private val items: List<CustomerItemModel>
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: CustomerItemModel, position: Int) {
        if (position == 0)
            binding.horizontalScrollHeader.visibility = View.VISIBLE
        else
            binding.horizontalScrollHeader.visibility = View.GONE

        if (position + 1 == items.size)
            binding.horizontalScrollFooter.visibility = View.VISIBLE
        binding.txtNumberItem.text = item.customerCode
        binding.txtNameCustomer.text = item.customerName
        binding.txtOrderWeight.text = Currency(item.orderWeight).toFormattedString()
        binding.txtInProgressOrder.text = Currency(item.inProgressOrderWeight).toFormattedString()
        binding.txtFinalWeight.text = Currency(item.finalWeight).toFormattedString()
        var sum_orderWeight = Currency.ZERO
        var sum_inProgressOrderWeight = Currency.ZERO
        var sum_finalWeight = Currency.ZERO
        items.forEach {
            sum_orderWeight = sum_orderWeight.add(it.orderWeight)
            sum_inProgressOrderWeight.add(it.inProgressOrderWeight)
            sum_finalWeight.add(it.finalWeight)
        }
        binding.txtFOrderWeight.text = sum_finalWeight.toFormattedString()
        binding.txtFFinalWeight.text = sum_orderWeight.toFormattedString()
        binding.txtFInFProgressOrder.text = sum_inProgressOrderWeight.toFormattedString()

    }



}