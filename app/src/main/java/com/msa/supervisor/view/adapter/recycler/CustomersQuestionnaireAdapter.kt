package com.msa.supervisor.view.adapter.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemCustomersQuestionnaireBinding
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.view.adapter.holder.CustomersQuestionnaireHolder
/**
 * create by Ali Soleymani.
 */
class CustomersQuestionnaireAdapter(
    private val items: MutableList<CustomerEntity>,
    private val click: CustomersQuestionnaireHolder.Click,
    private val context: Context
):RecyclerView.Adapter<CustomersQuestionnaireHolder>(){


    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomersQuestionnaireHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return CustomersQuestionnaireHolder(
            ItemCustomersQuestionnaireBinding.inflate(layoutInflater!!, parent, false)
            ,click,context)
    }

    override fun getItemCount(): Int=items.size

    override fun onBindViewHolder(holder: CustomersQuestionnaireHolder, position: Int) {
        holder.bind(items[position])
    }
}