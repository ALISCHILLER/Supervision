package com.msa.supervisor.view.adapter.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemApprovalsLayoutBinding
import com.msa.supervisor.model.data.response.approvals.ItemApprovalsModel
import com.msa.supervisor.view.adapter.holder.ApprovalsHolder
/**
 * create by Ali Soleymani.
 */
class ApprovalsAdapter(
    private val click: ApprovalsHolder.Click,
    private val items: MutableList<ItemApprovalsModel>,
    private val context: Context
) : RecyclerView.Adapter<ApprovalsHolder>() {


    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApprovalsHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return ApprovalsHolder(
            ItemApprovalsLayoutBinding.inflate(layoutInflater!!, parent, false),click,context
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ApprovalsHolder, position: Int) {
        holder.bind(items[position])
    }
}