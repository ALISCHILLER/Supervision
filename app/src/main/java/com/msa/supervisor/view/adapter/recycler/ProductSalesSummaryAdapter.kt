package com.msa.supervisor.view.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemProductSalesSummaryReportBinding
import com.msa.supervisor.model.data.response.report.ProductSalesSummaryReportModel
import com.msa.supervisor.view.adapter.holder.ProductSalesSummaryHolder
/**
 * create by Ali Soleymani.
 */
class ProductSalesSummaryAdapter (
    private val items: MutableList<ProductSalesSummaryReportModel>,
): RecyclerView.Adapter<ProductSalesSummaryHolder>() {

    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSalesSummaryHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return ProductSalesSummaryHolder(
            ItemProductSalesSummaryReportBinding.inflate(layoutInflater!!,parent,false))
    }

    override fun getItemCount()=items.size

    override fun onBindViewHolder(holder: ProductSalesSummaryHolder, position: Int) {
        holder.bind(items[position])
    }

}