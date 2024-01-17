package com.msa.supervisor.view.fragment.confirmorders

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.zar.core.enums.EnumApiError
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentOrderConfirmDetailsBinding
import com.msa.supervisor.model.data.request.DataConfrimOrderReguest
import com.msa.supervisor.model.data.response.order.ItemOrderModel
import com.msa.supervisor.model.data.response.order.OrderConfirmModel
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.adapter.recycler.OrderDetailsAdapter
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class OrderConfirmDetailsFragment(override var layout: Int = R.layout.fragment_order_confirm_details) :
    BaseFragment<FragmentOrderConfirmDetailsBinding>() {

    private val viewModel: ConfirmOrdersViewModel by viewModels()
    private val args: OrderConfirmDetailsFragmentArgs by navArgs()
    private lateinit var item: OrderConfirmModel
    private lateinit var adapter: OrderDetailsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item = args.itemOrderConfirmDetails!!
        initView()
        setListener()
        observeLiveDate()
    }

    private fun initView() {
        binding.txtNumberOrder.text = item.orderNumber
        binding.txtDate.text = item.orderDate
        binding.txtDealerCode.text = item.dealerCode
        binding.txtNameDealer.text = item.dealerName
        binding.txtCustomerCode.text = item.customerCode
        binding.txtNameCustomer.text = item.customerName
        binding.txtTypeCustomer.text = item.customerCategory
        binding.txtPaymentType.text = item.paymentType
        binding.txtComment.text = item.comment

        adapter = OrderDetailsAdapter(items = item.items as MutableList<ItemOrderModel>)

        val layoutManagerVertical = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.recyclerDetailsOrder.adapter = adapter
        binding.recyclerDetailsOrder.layoutManager = layoutManagerVertical
    }

    private fun setListener() {
        binding.btnConfirm.setOnClickListener {
            lottieisAnimating(false)
            val data = DataConfrimOrderReguest(listOf(item.orderNumber), "r2")
            viewModel.requestSendDataOrderConfirm(data)
        }
    }

    private fun observeLiveDate() {
        viewModel.errorLiveDate.observe(viewLifecycleOwner) {
            lottieisAnimating(true)
            showMessage(it.message)
            when (it.type) {
                EnumApiError.UnAuthorization -> (activity as MainActivity?)?.gotoFirstFragment()
                else -> {}
            }
        }
        viewModel.confirmOrderSend.observe(viewLifecycleOwner) {
            lottieisAnimating(true)
            onBackPressed()

        }

    }

    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
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