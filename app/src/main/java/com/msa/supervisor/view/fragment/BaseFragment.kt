package com.msa.supervisor.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.zar.core.enums.EnumApiError
import com.msa.supervisor.view.activity.MainActivity

/**
 * Created by m-latifi on 10/8/2022.
 */

abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    abstract var layout : Int
    protected lateinit var binding : DB


    //---------------------------------------------------------------------------------------------- onCreateView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    //---------------------------------------------------------------------------------------------- onCreateView

    open fun onBackPressed() {
        activity?.let {
            (it as MainActivity).popFragment()
        }
    }



    fun showMessage(message: String, type: EnumApiError = EnumApiError.Warning) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }
    //---------------------------------------------------------------------------------------------- showMessage


    //---------------------------------------------------------------------------------------------- gotoFragment
//    protected fun gotoFragment(fragment: Int, bundle: Bundle? = null) {
//        try {
//            findNavController().navigate(fragment, bundle)
//        } catch (e: java.lang.Exception){
//            findNavController().navigate(R.id.action_goto_SplashFragment)
//        }
//    }
}