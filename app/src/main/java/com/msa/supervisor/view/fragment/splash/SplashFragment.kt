package com.msa.supervisor.view.fragment.splash

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zar.core.enums.EnumApiError
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentSplashBinding
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.custom.Latifi
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by  Ali Soleymani.
 */


@AndroidEntryPoint
class SplashFragment(override var layout: Int = R.layout.fragment_splash) :
    BaseFragment<FragmentSplashBinding>() {

    private val splashViewModel: SplashViewModel by viewModels()

    //---------------------------------------------------------------------------------------------- onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = splashViewModel
        binding.imageViewManegerApp.visibility = View.INVISIBLE
        binding.materialButtonLogin.visibility = View.INVISIBLE
        binding.constraintLayout.visibility = View.INVISIBLE
        val drawable: Drawable = ShapeDrawable(
            Latifi(
                requireContext()
                    .getColor(R.color.lightsaberblue),
                requireContext().getColor(R.color.frightnight),
                5f
            )
        )
        binding.constraintLayoutParent.setBackgroundDrawable(drawable)

        setListener()
        startAnimation(true)
        checkUserIsLogged()
        initView()
        observeLiveDate()
    }
    //---------------------------------------------------------------------------------------------- onViewCreated


    private fun initView() {

        var versionName =""
        try {
            val pInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            versionName = pInfo.versionName
            Log.d("MyApp", "Version Name : $versionName")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        binding.versionApp.text=getString(R.string.versionFotter,versionName)
    }
    //---------------------------------------------------------------------------------------------- showMessage
    private fun showMessage(message: String) {
        binding.materialButtonLogin.stopLoading()
        startAnimation(false)
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }
    //---------------------------------------------------------------------------------------------- showMessage


    //---------------------------------------------------------------------------------------------- setListener
    private fun setListener() {
        binding.materialButtonLogin.setOnClickListener {
            checkSetting()
        }

//        binding.imageViewManegerApp.setOnClickListener {
////            findNavController().navigate(R.id.action_splashFragment_to_HomeFragment)
//        }
    }
    //---------------------------------------------------------------------------------------------- setListener


    //---------------------------------------------------------------------------------------------- startAnimation
    private fun startAnimation(b: Boolean) {
        CoroutineScope(Main).launch {
            delay(1000)
            if (context != null) {
                val slideInBottom =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_bottom)
                if (b) {
                    binding.constraintLayout.animation = null
                    val slideInLeft =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
                    binding.constraintLayout.startAnimation(slideInLeft)
                    binding.constraintLayout.startAnimation(slideInBottom)
                    binding.constraintLayout.visibility = View.VISIBLE

                } else if (binding.materialButtonLogin.visibility != View.VISIBLE){
                    binding.materialButtonLogin.animation = null
                    binding.materialButtonLogin.startAnimation(slideInBottom)
                    binding.materialButtonLogin.visibility = View.VISIBLE
                }

            }
        }

        CoroutineScope(Main).launch {
            delay(2000)
            if (context != null) {
                binding.constraintLayout.animation = null
                binding.constraintLayout.visibility = View.INVISIBLE
                val alpha =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.alpha)
                binding.constraintLayout.startAnimation(alpha)
                binding.constraintLayout.visibility = View.VISIBLE
            }
        }
    }
    //---------------------------------------------------------------------------------------------- startAnimation


    //---------------------------------------------------------------------------------------------- checkUserIsLogged
    private fun checkUserIsLogged() {
        CoroutineScope(Main).launch {
            delay(4000)
            if (splashViewModel.userIsEntered())
                gotoFragmentHome()
            else
                checkSetting()
        }

    }
    //---------------------------------------------------------------------------------------------- checkUserIsLogged


    //---------------------------------------------------------------------------------------------- gotoFragmentLogin
    private fun checkSetting() {
        if (binding.materialButtonLogin.isLoading)
            return
        binding.materialButtonLogin.startLoading(getString(R.string.bePatient))
        splashViewModel.requestSetting()
    }
    //---------------------------------------------------------------------------------------------- gotoFragmentLogin


    //---------------------------------------------------------------------------------------------- gotoFragmentHome
    private fun gotoFragmentHome() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }
    //---------------------------------------------------------------------------------------------- gotoFragmentHome


    //---------------------------------------------------------------------------------------------- observeLiveDate
    private fun observeLiveDate() {
        splashViewModel.errorLiveDate.observe(viewLifecycleOwner) {
            showMessage(it.message)
            when (it.type) {
                EnumApiError.UnAuthorization -> (activity as MainActivity?)?.gotoFirstFragment()
                else -> {}
            }
        }

        splashViewModel.successLiveData.observe(viewLifecycleOwner) {
            binding.materialButtonLogin.stopLoading()

            if (it)
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }

    }
    //---------------------------------------------------------------------------------------------- observeLiveDate


}