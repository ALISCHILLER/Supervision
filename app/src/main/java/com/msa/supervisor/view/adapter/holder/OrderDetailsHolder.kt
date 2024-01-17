package com.msa.supervisor.view.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemOrderDetailsBinding
import com.msa.supervisor.databinding.ItemOrederConfirmBinding
import com.msa.supervisor.model.data.response.order.ItemOrderModel
import com.msa.supervisor.model.data.response.order.OrderConfirmModel

class OrderDetailsHolder(
    val binding: ItemOrderDetailsBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: ItemOrderModel){
        binding.txtCodeProduct.text=item.productCode
        binding.txtGroupProduct.text=item.productCategory
        binding.txtNumber.text=item.productCountStr
        binding.txtNameProduct.text=item.productName

    }
}