package com.msa.supervisor.view.fragment.news

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentNewsBinding
import com.msa.supervisor.model.data.response.news.NewsModel
import com.msa.supervisor.view.adapter.holder.NewsHolder
import com.msa.supervisor.view.adapter.recycler.NewsAdapter
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class NewsFragment(override var layout: Int = R.layout.fragment_news) :
    BaseFragment<FragmentNewsBinding>() {

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeLiveDate()
        setListener()
    }

    private fun initView() {
        lottieisAnimating(false)
        viewModel.requestNews()

    }

    private fun setListener() {

    }

    private fun observeLiveDate() {
        viewModel.errorLiveDate.observe(viewLifecycleOwner) {
            lottieisAnimating(true)
            showMessage(it.message)
        }

        viewModel.newsModel.observe(viewLifecycleOwner) {
            val adapter = NewsAdapter(it, click)
            val layoutManagerVertical = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.recyclerNews.layoutManager = layoutManagerVertical
            binding.recyclerNews.adapter = adapter
            lottieisAnimating(true)
            it.takeIf { it.size>0 }?.let {
                binding.constraintLayout22.visibility=View.VISIBLE
            }
        }
    }

    var click = object : NewsHolder.OnClick {
        override fun selectItem(item: NewsModel) {

        }
    }


    private fun lottieisAnimating(runAn: Boolean) {
        if (binding.loading.isAnimating || runAn) {
            binding.loading.pauseAnimation()
            binding.loading.cancelAnimation()
            binding.loading.visibility = View.GONE
        } else {
            binding.loading.setAnimation("loading_confirm.json")
            binding.loading.setBackgroundColor(Color.TRANSPARENT)
            binding.loading.loop(true)
            binding.loading.playAnimation()
            binding.loading.visibility = View.VISIBLE
        }
    }

}