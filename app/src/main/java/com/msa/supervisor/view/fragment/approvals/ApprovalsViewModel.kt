package com.msa.supervisor.view.fragment.approvals

import com.msa.supervisor.model.data.response.approvals.ItemApprovalsModel
import com.msa.supervisor.view.fragment.BaseViewModel
import javax.inject.Inject

class ApprovalsViewModel @Inject constructor():BaseViewModel() {



    fun getItemApprovals(): MutableList<ItemApprovalsModel> {
        val items: MutableList<ItemApprovalsModel> = mutableListOf()

        items.add(ItemApprovalsModel(1, "تایید مشتری", "customer_confirm.json"))
        items.add(ItemApprovalsModel(2, "تایید سفارش", "confirm_order.json"))
        items.add(ItemApprovalsModel(3, "تایید پین کد","list_pincode.json"))
        return items
    }

}