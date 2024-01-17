package com.msa.supervisor.view.fragment.pincodeconfirm



import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentPinCodeConfirmBinding
import com.msa.supervisor.model.data.database.entity.PinCodeConfirmEntity
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.adapter.holder.PinCodeConfirmHolder
import com.msa.supervisor.view.adapter.recycler.PinCodeConfirmAdapter
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class PinCodeConfirmFragment(
    override var layout: Int = R.layout.fragment_pin_code_confirm
) : BaseFragment<FragmentPinCodeConfirmBinding>() {

    private val viewModel: PinCodeConfirmViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveDate()
        setListener()
        initView()
        setAnimation()
    }

    private fun setAnimation() {
    }

    private fun initView() {

        viewModel.getListPinCodeConfirm()
    }




    private fun observeLiveDate() {
        viewModel.errorLiveDate.observe(viewLifecycleOwner) {
            showMessage(it.message)
        }

        viewModel.pincodesConfirmLive.observe(viewLifecycleOwner){
            val adapter=PinCodeConfirmAdapter(it,clickConfirm,clickFailure)

            binding.recyclerPinCode.adapter=adapter

            val layoutManagerVertical = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            binding.recyclerPinCode.layoutManager=layoutManagerVertical

        }


        viewModel.sendPinCode.observe(viewLifecycleOwner){

        }

    }

    private fun  setListener() {
    }
    val clickConfirm=object:PinCodeConfirmHolder.ClickConfirm{
        override fun selectItem(pinCodeConfirm: PinCodeConfirmEntity) {
            Log.i(TAG, "selectItemConfirm: $pinCodeConfirm ")
            viewModel.requestrequestPinCodeApprove(pinCodeConfirm)
        }
    }
    val clickFailure=object:PinCodeConfirmHolder.ClickFailure{
        override fun selectItem(pinCodeFailure: PinCodeConfirmEntity) {
            Log.i(TAG, "selectItemFailure: $pinCodeFailure ")
        }
    }
    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }
}