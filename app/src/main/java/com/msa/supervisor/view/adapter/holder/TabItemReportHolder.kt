package com.msa.supervisor.view.adapter.holder

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.R
import com.msa.supervisor.databinding.ItemTapReportBinding
import com.msa.supervisor.model.data.response.report.ItemReportModel

class TabItemReportHolder(
    private val binding:ItemTapReportBinding,
    private val click:Click,
    private val context:Context

):RecyclerView.ViewHolder(binding.root){

    interface Click {
        fun selectItem(item:ItemReportModel,position: Int)
    }
    
    @SuppressLint("ResourceAsColor")
    fun bind(item:ItemReportModel, position: Int,selectPosition: Int){
        binding.item=item
        binding.root.setOnClickListener {
            click.selectItem(item,position)
        }
        chengColor(position,selectPosition)
        binding.executePendingBindings()
    }


    fun chengColor(position: Int,selectPosition: Int){
        if (selectPosition==position) {
            binding.layoutback.background =
                ContextCompat.getDrawable(context, R.drawable.drawable_button)
            binding.txtname.setTextColor(ContextCompat.getColor(context, R.color.white))
        }else{
            binding.layoutback.background =
                ContextCompat.getDrawable(context, R.drawable.drawable_tap_item_report)
            binding.txtname.setTextColor(ContextCompat.getColor(context, R.color.frightnight))
        }
    }
}