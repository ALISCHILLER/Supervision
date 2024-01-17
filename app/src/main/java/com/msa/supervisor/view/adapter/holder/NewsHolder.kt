package com.msa.supervisor.view.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemNewsHolderBinding
import com.msa.supervisor.model.data.response.news.NewsModel

class NewsHolder(
    val binding:ItemNewsHolderBinding,
    private val click: OnClick
):RecyclerView.ViewHolder(binding.root){
    interface OnClick{
        fun selectItem(item: NewsModel)
    }
    fun bind(item: NewsModel){
        binding.root.setOnClickListener {
           click.selectItem(item)
        }
        binding.txtTitle.text=item.title
    }
}