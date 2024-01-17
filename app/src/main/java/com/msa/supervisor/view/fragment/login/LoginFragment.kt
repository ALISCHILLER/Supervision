package com.msa.supervisor.view.fragment.login

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zar.core.tools.BiometricTools
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentLoginBinding
import com.msa.supervisor.ext.hideKeyboard
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.custom.Latifi
import com.msa.supervisor.view.dialog.progress.ProgressBarDialog
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by m-latifi on 11/9/2022.
 */

@AndroidEntryPoint
class LoginFragment(override var layout: Int = R.layout.fragment_login) :
    BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var biometricTools: BiometricTools

    private val loginViewModel: LoginViewModel by viewModels()

    private  var progressBarDialog: ProgressBarDialog?=null
    //---------------------------------------------------------------------------------------------- OnBackPressedCallback
    private val backClick = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            context?.let {
/*                ConfirmDialog(
                    it,
                    getString(R.string.doYouWantToExitApp),
                    object : ConfirmDialog.Click {
                        override fun clickYes() {
                            activity?.finish()
                        }
                    }).show()*/
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = loginViewModel
        initView()
        setListener()
    }




    @SuppressLint("StringFormatMatches")
    private fun initView() {

        val drawable: Drawable = ShapeDrawable(
            Latifi(requireContext()
            .getColor(R.color.lightsaberblue)
            ,requireContext().getColor(R.color.frightnight),5f)
        )
        binding.layoutBack.setBackgroundDrawable(drawable)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, backClick)
        activity?.let { (it as MainActivity).deleteAllData() }
/*        if (loginViewModel.isBiometricEnable()) {
            binding.cardViewFingerPrint.visibility = View.VISIBLE
        } else {
            binding.cardViewFingerPrint.visibility = View.INVISIBLE
        }*/
        observeLiveDate()
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
    //---------------------------------------------------------------------------------------------- initView


    //---------------------------------------------------------------------------------------------- observeLiveDate
    private fun observeLiveDate() {
        loginViewModel.errorLiveDate.observe(viewLifecycleOwner) {
            binding.buttonLogin.stopLoading()
            if (progressBarDialog !=null)
                CoroutineScope(IO).launch {
                    delay(2000)
                    progressBarDialog?.stop()
                }
            showMessage(it.message)
        }

        loginViewModel.loginLiveDate.observe(viewLifecycleOwner) {
             typeRequest(it)
        }

        loginViewModel.userNameError.observe(viewLifecycleOwner) {
            binding.textInputLayoutUserName.error = it
            stopLoading()
        }

        loginViewModel.passwordError.observe(viewLifecycleOwner) {
            binding.textInputLayoutPasscode.error = it
            stopLoading()
        }


        loginViewModel.requestTroue.observe(viewLifecycleOwner){
            progress(10.0)
        }
        loginViewModel.requestTroueEnd.observe(viewLifecycleOwner){
            if (progressBarDialog !=null)
              CoroutineScope(IO).launch {
                  progress(100.0)
                  delay(1000)
                  progressBarDialog?.stop()
                  withContext(Main) {
                      findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                  }
              }

        }

    }



    fun typeRequest(type: Int){
        loginViewModel.requestVisitor(type)
        progressBarDialog = ProgressBarDialog(requireContext(), "درحال دریافت اطلاعات")
        progressBarDialog?.startLoading()
    }
    //---------------------------------------------------------------------------------------------- observeLiveDate

    fun progress(d:Double) {
        CoroutineScope(IO).launch {
                withContext(Main) {
                    progressBarDialog?.setProgress(d)
                }
            }
        }



    //---------------------------------------------------------------------------------------------- showMessage
    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
        stopLoading()
    }
    //---------------------------------------------------------------------------------------------- showMessage


    //---------------------------------------------------------------------------------------------- setListener
    private fun setListener() {

        binding
            .buttonLogin
            .setOnClickListener { login(false) }

/*        binding
            .cardViewFingerPrint
            .setOnClickListener { showBiometricDialog() }*/

       /* binding.imageViewWave.setOnLongClickListener {
            if (context != null) {
                val dialog = DialogManager().createDialogHeightWrapContent(
                    requireContext(),
                    R.layout.dialog_confirm_ip,
                    Gravity.CENTER,
                    0
                )

                val textInputEditTextIp =
                    dialog.findViewById<TextInputEditText>(R.id.textInputEditTextIp)

                val textInputEditTextIpPassword =
                    dialog.findViewById<TextInputEditText>(R.id.textInputEditTextIpPassword)

                val buttonNo = dialog.findViewById<MaterialButton>(R.id.buttonNo)
                buttonNo.setOnClickListener { dialog.dismiss() }

                val buttonYes = dialog.findViewById<MaterialButton>(R.id.buttonYes)
                buttonYes.setOnClickListener {

                    if (textInputEditTextIp.text.toString().isIP()) {
                        if (textInputEditTextIpPassword.text.toString() != "holeshdaf"){
                            textInputEditTextIpPassword.error = getString(R.string.passwordIsInCorrect)
                            return@setOnClickListener
                        }
                        loginViewModel.saveNewIp(textInputEditTextIp.text.toString())
                        showMessage(getString(R.string.updateIsSuccess))
                        CoroutineScope(Main).launch {
                            delay(1500)
                            (activity as MainActivity).finish()
                        }
                    } else {
                        textInputEditTextIp.error = getString(R.string.ipIsIncorrect)
                    }
                }
                dialog.show()
            }
            return@setOnLongClickListener true
        }*/
    }
    //---------------------------------------------------------------------------------------------- setListener


    //---------------------------------------------------------------------------------------------- showBiometricDialog
    private fun showBiometricDialog() {
        if (activity == null)
            return
        val executor = ContextCompat.getMainExecutor(requireContext())
        val biometricPrompt = BiometricPrompt(
            requireActivity(),
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showMessage(getString(R.string.onAuthenticationError))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    fingerPrintClick()
                }
            })
        biometricTools.checkDeviceHasBiometric(biometricPrompt)
    }
    //---------------------------------------------------------------------------------------------- showBiometricDialog


    //---------------------------------------------------------------------------------------------- fingerPrintClick
    private fun fingerPrintClick() {
        login(true)
    }
    //---------------------------------------------------------------------------------------------- fingerPrintClick


    //---------------------------------------------------------------------------------------------- login
    @SuppressLint("HardwareIds")
    private fun login(fromFingerPrint: Boolean) {
        if (context == null || binding.buttonLogin.isLoading)
            return
        startLoading()
        val androidId =
            Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
        loginViewModel.login(fromFingerPrint, androidId, "managerapp")
    }
    //---------------------------------------------------------------------------------------------- login



    //---------------------------------------------------------------------------------------------- startLoading
    private fun startLoading() {
        hideKeyboard()
        binding.textInputLayoutUserName.error = null
        binding.textInputLayoutPasscode.error = null
        binding.textInputEditTextUserName.isEnabled = false
        binding.textInputEditTextPassword.isEnabled = false
        binding.buttonLogin.startLoading(getString(R.string.bePatient))
    }
    //---------------------------------------------------------------------------------------------- startLoading


    //---------------------------------------------------------------------------------------------- stopLoading
    private fun stopLoading() {
        binding.textInputEditTextUserName.isEnabled = true
        binding.textInputEditTextPassword.isEnabled = true
        binding.buttonLogin.stopLoading()
    }
    //---------------------------------------------------------------------------------------------- stopLoading


}