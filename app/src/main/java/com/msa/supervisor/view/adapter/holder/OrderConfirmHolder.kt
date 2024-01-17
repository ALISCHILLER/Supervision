package com.msa.supervisor.view.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.R
import com.msa.supervisor.databinding.ItemOrederConfirmBinding
import com.msa.supervisor.model.data.response.order.OrderConfirmModel

class OrderConfirmHolder(
    private val binding: ItemOrederConfirmBinding,
    private val click: Click
) : RecyclerView.ViewHolder(binding.root) {
    interface Click {
        fun selectItem(item: OrderConfirmModel, sendData: Boolean)

        fun selectItemCheckBox(item: OrderConfirmModel, sendData: Boolean)

    }

    fun bind(item: OrderConfirmModel) {
        binding.item = item
        binding.root.setOnClickListener {
            click.selectItem(item, false)
        }
        binding.btnConfirm.setOnClickListener {
            click.selectItem(item, true)
        }
        chechOrderStatus(item)
        binding.iconOrderStatus.setOnClickListener {
            setListenerChechOrderStatus(item)

        }
        binding.txtNumberOrder.text = item.orderNumber
        binding.txtDate.text = item.orderDate
        binding.txtDealerCode.text = item.dealerCode
        binding.txtNameDealer.text = item.dealerName
        binding.txtCustomerCode.text = item.customerCode
        binding.txtNameCustomer.text = item.customerName
        binding.txtTypeCustomer.text = item.customerCategory
        binding.txtPaymentType.text = item.paymentType
        binding.txtComment.text=item.comment
    }


    private fun chechOrderStatus(item: OrderConfirmModel){
        if (item.orderStatus.equals("D2"))
            binding.iconOrderStatus.setImageResource(R.drawable.ic_disapproval)
        else
            binding.iconOrderStatus.setImageResource(R.drawable.ic_confirm)

    }

    private fun setListenerChechOrderStatus(item: OrderConfirmModel){
        if (item.orderStatus.equals("D2")) {
            binding.iconOrderStatus.setImageResource(R.drawable.ic_confirm)
            item.orderStatus=""
            click.selectItemCheckBox(item,true)
        }  else {
            binding.iconOrderStatus.setImageResource(R.drawable.ic_disapproval)
            item.orderStatus="D2"
            click.selectItemCheckBox(item,false)
        }
    }
}