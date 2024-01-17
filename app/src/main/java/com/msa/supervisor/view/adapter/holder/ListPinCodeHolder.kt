package com.msa.supervisor.view.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemPinCodeBinding
import com.msa.supervisor.model.data.response.customer.CustomerPinModel

class ListPinCodeHolder(
    val binding:ItemPinCodeBinding
):RecyclerView.ViewHolder(binding.root) {

    fun bind(item : CustomerPinModel){
        binding.txtCodeCustomer.text=item.customerCode
        binding.txtPinEdit.text=item.pin4
        binding.txtPinCheke.text=item.pin2
        binding.txtPinTotalReturn.text=item.pin3
        binding.txtPinReceived.text=item.pin1
        binding.txtNameCustomer.text=item.customerName
        binding.txtNameDealer.text=item.dealerName
        binding.textDate.text= item.pinPDate
    }
}