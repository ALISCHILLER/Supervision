package com.msa.supervisor.view.adapter.holder

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemApprovalsLayoutBinding
import com.msa.supervisor.model.data.response.approvals.ItemApprovalsModel
import com.msa.supervisor.model.data.response.report.ProductInvoiveBalanceReportModel
/**
 * create by Ali Soleymani.
 */
class ApprovalsHolder(
    private val binding: ItemApprovalsLayoutBinding,
    private val click: ApprovalsHolder.Click,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    interface Click {
        fun selectItem(item: ItemApprovalsModel)
    }

    fun bind(item: ItemApprovalsModel) {
        binding.item = item
        binding.root.setOnClickListener { click.selectItem(item) }
        binding.image.setAnimation(item.image)
        binding.image.setBackgroundColor(Color.TRANSPARENT)
        binding.image.loop(true)
        binding.image.playAnimation()
        binding.image.visibility = View.VISIBLE
    }
}