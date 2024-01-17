package com.msa.supervisor.view.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemOrderDetailsBinding
import com.msa.supervisor.model.data.response.order.ItemOrderModel
import com.msa.supervisor.model.data.response.order.OrderConfirmModel
import com.msa.supervisor.view.adapter.holder.OrderDetailsHolder
/**
 * create by Ali Soleymani.
 */
class OrderDetailsAdapter(
    private val items: MutableList<ItemOrderModel>
):RecyclerView.Adapter<OrderDetailsHolder>(){

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return OrderDetailsHolder(
            ItemOrderDetailsBinding.inflate(layoutInflater!!,parent,false))
    }


    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: OrderDetailsHolder, position: Int) {
        holder.bind(items[position])
    }


}