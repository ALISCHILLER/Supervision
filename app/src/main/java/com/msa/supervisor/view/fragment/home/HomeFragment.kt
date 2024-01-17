package com.msa.supervisor.view.fragment.home

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zar.core.enums.EnumApiError
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentHomeBinding
import com.msa.supervisor.model.data.response.visitor.VisitorVisitInfoModel
import com.msa.supervisor.tool.Currency
import com.msa.supervisor.tool.LatifiShape
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * create by m-latifi on 4/15/2023
 */

@AndroidEntryPoint
class HomeFragment(override var layout: Int = R.layout.fragment_home) :
    BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    //---------------------------------------------------------------------------------------------- onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeLiveDate()
        setAnimation()
        setListener()
    }
    //---------------------------------------------------------------------------------------------- onViewCreated


    //---------------------------------------------------------------------------------------------- showMessage
    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }
    //---------------------------------------------------------------------------------------------- showMessage


    //---------------------------------------------------------------------------------------------- initView
    private fun initView() {
        setHeaderDrawable()
        startChartAnimation()
        viewModel.requestVisitorInfo()
        viewModel.getUser()
    }
    //---------------------------------------------------------------------------------------------- initView


    //---------------------------------------------------------------------------------------------- setHeaderDrawable
    private fun setHeaderDrawable() {
        if (context != null) {
            ShapeDrawable(
                LatifiShape(
                    requireContext().getColor(R.color.primaryColor),
                    requireContext().getColor(R.color.primaryVariant),
                    requireContext().getColor(R.color.white),
                    50f
                )
            )
          //  binding.constraintLayoutHeader.setBackgroundDrawable(drawable)
        }
    }
    //---------------------------------------------------------------------------------------------- setHeaderDrawable


    //---------------------------------------------------------------------------------------------- startChartAnimation
    private fun startChartAnimation(){
        if (context == null)
            return

        binding.constraintLayoutTopRight.visibility = View.INVISIBLE
        binding.constraintLayoutTopLeft.visibility = View.INVISIBLE
        binding.constraintLayoutBottomRight.visibility = View.INVISIBLE
        binding.constraintLayoutBottomLeft.visibility = View.INVISIBLE
        val tRight: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.top_right)
        val tLeft: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.top_left)
        val bRight: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_right)
        val bLeft: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_left)

        CoroutineScope(Main).launch {
            delay(500)
            binding.constraintLayoutTopRight.startAnimation(tRight)
            binding.constraintLayoutTopRight.visibility = View.VISIBLE
            delay(300)
            binding.constraintLayoutTopLeft.startAnimation(tLeft)
            binding.constraintLayoutTopLeft.visibility = View.VISIBLE
            delay(100)
            binding.constraintLayoutBottomRight.startAnimation(bRight)
            binding.constraintLayoutBottomRight.visibility = View.VISIBLE
            delay(100)
            binding.constraintLayoutBottomLeft.startAnimation(bLeft)
            binding.constraintLayoutBottomLeft.visibility = View.VISIBLE
        }
    }
    //---------------------------------------------------------------------------------------------- startChartAnimation


    //---------------------------------------------------------------------------------------------- observeLiveDate
    private fun observeLiveDate() {
        viewModel.errorLiveDate.observe(viewLifecycleOwner) {
            showMessage(it.message)
            when (it.type) {
                EnumApiError.UnAuthorization -> (activity as MainActivity?)?.gotoFirstFragment()
                else -> {}
            }
        }

        viewModel.visitInfoModel.observe(viewLifecycleOwner){
            setData(it)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.textViewProfileName.text = it.personnelName
            }
        }
    }
    //---------------------------------------------------------------------------------------------- observeLiveDate

    fun setData(visitInfoModel: List<VisitorVisitInfoModel>){
        visitInfoModel.forEach {
            binding.numberofcustomersvisited.text= it.visitedCustomerCount.toString()
            binding.thenumberofvisitsontheroute.text= it.visited.toString()
            binding.txtordernumber.text= it.ordered.toString()
            binding.txtOrdervalue.text= it.totalAmount?.let { it1 -> Currency(it1).toFormattedString() }
            binding.txtcustomerall.text= it.customerCount.toString()
            binding.constraintLayoutNumberOfNonOrder.value=it.noOrder
            binding.constraintLayoutNumberOfBack.value=it.returnCount
            binding.constraintLayoutNumberOfNonVisit.value=it.noVisit
        }
    }

    fun setListener(){
        binding.lottPinCode.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listPinCodeFragment)
        }

        binding.linearLayoutQuestionnaire.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_customersQuestionnaireFragment)
        }
        binding.linearLayoutNews.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newsFragment)
        }
        binding.linearLayoutRequest.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pinCodeConfirmFragment)
        }
    }
    fun setAnimation(){
        binding.lottPinCode.setAnimation("listPinCode.json")
        binding.lottPinCode.setBackgroundColor(Color.TRANSPARENT)
        binding.lottPinCode.loop(true)
        binding.lottPinCode.playAnimation()


        binding.lottNews.setAnimation("news.json")
        binding.lottNews.loop(true)
        binding.lottNews.playAnimation()
        binding.lottNews.visibility = View.VISIBLE

        binding.lottQuestion.setAnimation("question5.json")
        binding.lottQuestion.loop(true)
        binding.lottQuestion.playAnimation()
        binding.lottQuestion.visibility = View.VISIBLE


        binding.confimPinCode.setAnimation("list_pincode.json")
        binding.confimPinCode.loop(true)
        binding.confimPinCode.playAnimation()
        binding.confimPinCode.visibility = View.VISIBLE
    }
}