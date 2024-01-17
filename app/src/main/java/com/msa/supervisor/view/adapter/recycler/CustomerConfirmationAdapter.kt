package com.msa.supervisor.view.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomerConfirmBinding
import com.msa.supervisor.model.data.database.entity.CustomerapprovalEntity
import com.msa.supervisor.model.data.response.customer.CustomerApprovalModel
import com.msa.supervisor.view.adapter.holder.CustomerConfirmationHolder
/**
 * create by Ali Soleymani.
 */
class CustomerConfirmationAdapter(
    private val click: CustomerConfirmationHolder.Click,
    private val items: MutableList<CustomerapprovalEntity>
): RecyclerView.Adapter<CustomerConfirmationHolder>(){

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerConfirmationHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return CustomerConfirmationHolder(
            ItemCustomerConfirmBinding.inflate(layoutInflater!!, parent, false),click
        )
    }

    override fun getItemCount()= items.size

    override fun onBindViewHolder(holder: CustomerConfirmationHolder, position: Int) {
        holder.bind(items[position])
    }
}