package com.msa.supervisor.view.adapter.holder

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomersQuestionnaireBinding
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.model.data.response.approvals.ItemApprovalsModel
import com.msa.supervisor.model.data.response.customer.CustomerModel

class CustomersQuestionnaireHolder(
    private val binding: ItemCustomersQuestionnaireBinding,
    private val click: Click,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    interface Click {
        fun selectItem(item: CustomerEntity)
    }

    fun bind(item: CustomerEntity) {
        binding.root.setOnClickListener { click.selectItem(item) }
        binding.txtNameCustomer.text = item.customerName
        binding.txtCodeCustomer.text = item.customerCode
        binding.txtPhone.text = item.phone
        binding.txtAddress.text = item.address
        binding.txtMobile.text = item.mobile
        binding.txtNameDealer.text = item.dealerName
        binding.iconPhone.setOnClickListener {
            makePhoneCall(item.phone.toString())
        }
    }


    fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(intent)
    }
}