package com.msa.supervisor.view.adapter.recycler.report
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemOrderStatusReportBinding
import com.msa.supervisor.model.data.response.report.OrderStatusReportModel
import com.msa.supervisor.view.adapter.holder.report.OrderStatusReportHolder
/**
 * create by Ali Soleymani.
 */
class OrderStatusReportAdapter(
    private val items: MutableList<OrderStatusReportModel>,
    val context: Context
) : RecyclerView.Adapter<OrderStatusReportHolder>() {

    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): OrderStatusReportHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return OrderStatusReportHolder(
            ItemOrderStatusReportBinding.inflate(layoutInflater!!,parent,false),items
            , context)
    }

    override fun getItemCount()=items.size

    override fun onBindViewHolder(holder: OrderStatusReportHolder, position: Int) {
        holder.bind(items[position],position)
    }
}