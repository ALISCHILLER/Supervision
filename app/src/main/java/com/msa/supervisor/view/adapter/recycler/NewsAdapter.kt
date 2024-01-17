package com.msa.supervisor.view.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemNewsHolderBinding
import com.msa.supervisor.model.data.response.news.NewsModel
import com.msa.supervisor.view.adapter.holder.NewsHolder
/**
 * create by Ali Soleymani.
 */
class NewsAdapter(
    private val items: List<NewsModel>,
    private val click: NewsHolder.OnClick
) : RecyclerView.Adapter<NewsHolder>() {


    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        return NewsHolder(
            ItemNewsHolderBinding.inflate(layoutInflater!!, parent, false),
            click
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(items[position])
    }
}