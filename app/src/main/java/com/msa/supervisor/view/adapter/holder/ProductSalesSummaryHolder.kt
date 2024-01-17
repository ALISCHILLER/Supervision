package com.msa.supervisor.view.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemProductSalesSummaryReportBinding
import com.msa.supervisor.model.data.response.report.ProductSalesSummaryReportModel
import com.msa.supervisor.tool.Currency


class ProductSalesSummaryHolder(
    val binding: ItemProductSalesSummaryReportBinding
) : RecyclerView.ViewHolder(binding.root) {



    interface Click {
        fun selectItem(item: ProductSalesSummaryReportModel)
    }

    fun bind(item: ProductSalesSummaryReportModel) {
        binding.item=item
        binding.txtNameProduct.text=item.productName
        binding.txtCodeProduct.text=item.productBackOfficeCode
        binding.txtcodeProductGroup.text=item.productCategoryCode
        binding.txtNameProductGroup.text=item.productCategoryName
        binding.txtNetWeight.text= Currency(item.productNetWeight).toString()
        binding.txtNetNumberOfCartons.text= item.productNetCount_CA.toInt().toString()
    }
}