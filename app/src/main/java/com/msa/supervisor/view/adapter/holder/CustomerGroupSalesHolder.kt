package com.msa.supervisor.view.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomerGroupSalesReportBinding
import com.msa.supervisor.model.data.response.report.CustomerSalesSummaryReportModel
import com.msa.supervisor.tool.Currency
/**
 * create by Ali Soleymani.
 */
class CustomerGroupSalesHolder(
    val binding:ItemCustomerGroupSalesReportBinding,
) : RecyclerView.ViewHolder(binding.root) {

    interface Click {
        fun selectItem(item: CustomerSalesSummaryReportModel)
    }

    fun bind(item: CustomerSalesSummaryReportModel) {
        binding.item=item
        binding.txtGroupCustomer?.text=item.customerGroupTXT
        binding.txtCustomerActivity?.text=item.customerActivityTXT
        binding.txtNetWeight?.text= Currency(item.netWeight).toString()
        binding.txtNetNumberOfCartons?.text=Currency(item.netCount_CA).toString()
    }


}