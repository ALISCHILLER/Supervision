package com.msa.supervisor.view.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomerNosaleBinding
import com.msa.supervisor.model.data.response.report.CustomerNoSaleReportModel
/**
 * create by Ali Soleymani.
 */
class customerNoSaleHolder(
    val binding: ItemCustomerNosaleBinding
) : RecyclerView.ViewHolder(binding.root) {

    interface Click {
        fun selectItem(item: CustomerNoSaleReportModel)
    }

    fun bind(item: CustomerNoSaleReportModel) {
        binding.item = item
        binding.txtMobile.text = item.mobile
        binding.txtPhone.text = item.phone
        binding.txtaddress.text = item.address
        binding.txtNameCustomer.text = item.customerName
        binding.txtCodeCustomer.text = item.customerCode
        binding.txtActivity.text = item.customerActivity
        binding.txtNameShop.text = item.storeName
    }

}