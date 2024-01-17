package com.msa.supervisor.view.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemInvoiceBalanceReportBinding
import com.msa.supervisor.model.data.response.report.ProductInvoiveBalanceReportModel
import com.msa.supervisor.tool.Currency

class InvoiveBalanceHolder(
    private val binding: ItemInvoiceBalanceReportBinding,
    private val click: Click
) : RecyclerView.ViewHolder(binding.root) {

    interface Click {
        fun selectItem(item: ProductInvoiveBalanceReportModel)
    }

    fun bind(item: ProductInvoiveBalanceReportModel) {
        binding.item = item
        binding.root.setOnClickListener { click.selectItem(item) }
        binding.btnShow.setOnClickListener {
            setexpandRounting(!binding.layoutExpandble.isExpanded)
        }
        binding.txtPriceInoviceBalance.text=Currency(item.ivoiceRemain).toFormattedString()
        binding.txtRemittanceAmount.text=Currency(item.paidPose).toFormattedString()
        binding.txtCashAmount.text=Currency(item.paidCash).toFormattedString()
        binding.txtCheckAmount.text=Currency(item.paidCheck).toFormattedString()
        binding.txtClearingAmount.text= Currency(item.usancePaid).toFormattedString()
        binding.txtDateFactor.text= item.invoiceShmsiDate
        binding.txtFinalAmountInvoice.text=Currency(item.invoiceFinalPrice).toFormattedString()
        binding.txtNameCustomer.text=item.customerName
        binding.executePendingBindings()
    }


    private fun setexpandRounting(cheak: Boolean) {
        if (cheak)
            binding.layoutExpandble.expand()
        else
            binding.layoutExpandble.collapse()
    }
}