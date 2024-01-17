package com.msa.supervisor.view.fragment.approvals


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentApprovalsBinding
import com.msa.supervisor.model.data.response.approvals.ItemApprovalsModel
import com.msa.supervisor.view.adapter.holder.ApprovalsHolder
import com.msa.supervisor.view.adapter.recycler.ApprovalsAdapter
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
/**
 * create by Ali Soleymani.
 */

@AndroidEntryPoint
class ApprovalsFragment(override var layout: Int = R.layout.fragment_approvals) :
    BaseFragment<FragmentApprovalsBinding>() {

    private val viewModel: ApprovalsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        val click = object : ApprovalsHolder.Click {
            override fun selectItem(item: ItemApprovalsModel) {
                Log.e("TAG", "selectItem: ${item.name}")
                navControl(item.id)
            }
        }

        val adapter = ApprovalsAdapter(click, viewModel.getItemApprovals(),requireContext())

        val layoutManagerVertical = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.recylerapprovals.adapter = adapter
        binding.recylerapprovals.layoutManager = layoutManagerVertical
    }


    fun navControl(id:Int){
        when(id){
            1-> findNavController().navigate(R.id.action_approvalsFragment_to_customerconfirmationFragment)
            2-> findNavController().navigate(R.id.action_approvalsFragment_to_confirmOrdersFragment)
            3-> findNavController().navigate(R.id.action_approvalsFragment_to_pinCodeConfirmFragment)

        }
    }



}