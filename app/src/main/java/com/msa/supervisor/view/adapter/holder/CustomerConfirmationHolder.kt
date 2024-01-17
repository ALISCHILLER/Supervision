package com.msa.supervisor.view.adapter.holder


import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomerConfirmBinding
import com.msa.supervisor.model.data.database.entity.CustomerapprovalEntity
import com.msa.supervisor.model.data.response.customer.CustomerApprovalModel
/**
 * create by Ali Soleymani.
 */
class CustomerConfirmationHolder (
    private val binding: ItemCustomerConfirmBinding,
    private val click: CustomerConfirmationHolder.Click
) : RecyclerView.ViewHolder(binding.root) {


    interface Click {
        fun selectItem(item: CustomerapprovalEntity,check:Boolean)
    }
    fun bind(item: CustomerapprovalEntity) {

        binding.btnConfirm.setOnClickListener {
            click.selectItem(item,true)
        }
        binding.btnFailure.setOnClickListener {
            click.selectItem(item,false)
        }
        binding.executePendingBindings()
        binding.btnShowHiden.setOnClickListener {
            setexpandRounting(!binding.expandableMore.isExpanded)
        }
        binding.txtcodecustomer.text=item.customerCode
        binding.txtnamecustomer.text=item.customerName
        binding.txtMobile.text=item.mobile
        binding.txtaddress.text=item.address
        binding.txtOldAddress.text=item.address
        binding.txtNewAddress.text=item.newAddress
        binding.txtOldCode.text=item.codeNaghs
        binding.txtNewCode.text=item.newCodeNaghdh
        binding.txtOldPhone.text=item.mobile
        binding.txtNewPhone.text=item.newMobile

    }

    private fun setexpandRounting(cheak: Boolean) {
        if (cheak)
            binding.expandableMore.expand()
        else
            binding.expandableMore.collapse()
    }
}