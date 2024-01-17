package com.msa.supervisor.view.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomerNosaleBinding
import com.msa.supervisor.databinding.ItemPincodeConfirmBinding
import com.msa.supervisor.model.data.database.entity.PinCodeConfirmEntity
import com.msa.supervisor.view.adapter.holder.PinCodeConfirmHolder
import com.msa.supervisor.view.adapter.holder.customerNoSaleHolder
/**
 * create by Ali Soleymani.
 */
class PinCodeConfirmAdapter(
    val items: List<PinCodeConfirmEntity>,
    private val clickConfirm: PinCodeConfirmHolder.ClickConfirm,
    private val clickFailure: PinCodeConfirmHolder.ClickFailure
) : RecyclerView.Adapter<PinCodeConfirmHolder>() {
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinCodeConfirmHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return PinCodeConfirmHolder(
            ItemPincodeConfirmBinding.inflate(layoutInflater!!, parent, false),
            clickConfirm,
            clickFailure
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PinCodeConfirmHolder, position: Int) {
        holder.bind(items[position])
    }

}