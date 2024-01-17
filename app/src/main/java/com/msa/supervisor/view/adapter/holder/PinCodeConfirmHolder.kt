package com.msa.supervisor.view.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemPincodeConfirmBinding
import com.msa.supervisor.model.data.database.entity.PinCodeConfirmEntity
import com.msa.supervisor.model.data.response.report.CustomerNoSaleReportModel

class PinCodeConfirmHolder(
    private val binding: ItemPincodeConfirmBinding,
    private val clickConfirm:ClickConfirm,
    private val clickFailure:ClickFailure
) : RecyclerView.ViewHolder(binding.root) {
    interface ClickConfirm {
        fun selectItem(pinCodeConfirm: PinCodeConfirmEntity)
    }

    interface ClickFailure {
        fun selectItem(pinCodeFailure: PinCodeConfirmEntity)
    }

    fun bind(item: PinCodeConfirmEntity) {
        binding.txtTypePinCode.text=item.pinType
        binding.txtDate.text=item.date
        binding.txtNameCustomer.text=item.customerName
        binding.txtNameDelear.text=item.dealerName

        binding.btnConfirm.setOnClickListener {
            clickConfirm.selectItem(item)
        }
        binding.btnFailure.setOnClickListener {
            clickFailure.selectItem(item)
        }
    }
}