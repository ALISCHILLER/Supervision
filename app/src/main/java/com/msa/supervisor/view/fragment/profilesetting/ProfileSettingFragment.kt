package com.msa.supervisor.view.fragment.profilesetting


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.msa.supervisor.view.dialog.multiplechoice.SingleSelectionCompleteListener
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentProfileSettingBinding
import com.msa.supervisor.tool.CompanionValues
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import com.msa.supervisor.view.dialog.multiplechoice.SingleChoiceDialog
import com.msa.supervisor.view.dialog.progress.ProgressBarDialog
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class ProfileSettingFragment(
    override var layout: Int = R.layout.fragment_profile_setting
) : BaseFragment<FragmentProfileSettingBinding>() {
    private val viewModel: ProfileSettingViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val listBranchItems: MutableList<MultiItem> = ArrayList()
    private var progressBarDialog: ProgressBarDialog? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveDate()
        setListener()
        initView()
        setAnimation()
    }


    private fun initView() {
        viewModel.getListOfBranches()
        viewModel.getUser()

        lateinit var versionName:String
        try {
            val pInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            versionName = pInfo.versionName
            Log.d("MyApp", "Version Name : $versionName")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        binding.versionApp.text=getString(R.string.versionFotter,versionName)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun observeLiveDate() {
        viewModel.errorLiveDate.observe(viewLifecycleOwner) {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(1000)
                    withContext(Main){
                        lottieisAnimating(true)
                    }
                    if (progressBarDialog != null)
                    progressBarDialog?.stop()
                }
            showMessage(it.message)
        }


        viewModel.listBranchItems.observe(viewLifecycleOwner) {
            listBranchItems.clear()
            listBranchItems.addAll(it)
        }


        viewModel.requestTroue.observe(viewLifecycleOwner) {
            progress(10.0)
        }
        viewModel.requestTroueEnd.observe(viewLifecycleOwner) {
            if (progressBarDialog != null)
                CoroutineScope(Dispatchers.IO).launch {
                    progress(100.0)
                    delay(2000)
                    progressBarDialog?.stop()
                }
        }
        viewModel.user.observe(viewLifecycleOwner){
            if (it != null) {
                binding.txtUser.text=it.personnelName
                binding.txtCodeUser.text=it.personnelCode
            }

        }


        viewModel.logout.observe(viewLifecycleOwner){
            CoroutineScope(Dispatchers.IO).launch {
                delay(1000)
                withContext(Main) {
                    lottieisAnimating(true)
                }
            }
            requireActivity().finish()
        }
    }

    private fun setListener() {
        binding.layoutList.setOnClickListener {
            SingleChoiceDialog.show(
                requireContext(),
                "لیست شعب",
                R.drawable.ic_visitor,
                listBranchItems,
                object : SingleSelectionCompleteListener {
                    override fun onCompleteSelection(selectedItem: MultiItem) {
                        Log.e(TAG, "onCompleteSelection: $selectedItem")
                        binding.txtBranch.text = selectedItem.name
                        val fields = selectedItem.id.split(":")
                        sharedPreferences
                            .edit()
                            .putString(CompanionValues.zoneIds, fields.get(2)).apply()
                        typeRequest()
                    }

                }
            )

        }
        binding.layoutquestion.setOnClickListener {
            findNavController().navigate(R.id.action_profileSettingFragment_to_customersQuestionnaireFragment)
        }
        binding.layoutNews.setOnClickListener {
            findNavController().navigate(R.id.action_profileSettingFragment_to_newsFragment)
        }


        binding.layoutUpdate.setOnClickListener {
            val url = "http://5.160.125.98:8080/content/apk/supervisor.apk"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.layoutLogout.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("خروج از برنامه")
            builder.setMessage("آیا مطمئن هستید که می خواهید این عمل را انجام دهید؟")

            builder.setPositiveButton("تایید") { dialog, which ->
                lottieisAnimating(false)
                viewModel.requestLogout()
            }

            builder.setNegativeButton("انصراف") { dialog, which ->
                // User clicked No
                // Do nothing or handle the cancellation here
            }

            builder.show()




        }

    }


    fun typeRequest() {
        viewModel.requestVisitor()
        progressBarDialog = ProgressBarDialog(requireContext(), "درحال دریافت اطلاعات")
        progressBarDialog?.startLoading()
    }


    fun progress(f: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                progressBarDialog?.setProgress(f)
            }
        }
    }



    private fun lottieisAnimating(runAn: Boolean) {
        if (binding.loading.isAnimating || runAn) {
            binding.loading.pauseAnimation()
            binding.loading.cancelAnimation()
            binding.loading.visibility = View.GONE
        } else {
            binding.loading.setAnimation("logout2.json")
            binding.loading.setBackgroundColor(Color.TRANSPARENT)
            binding.loading.loop(true)
            binding.loading.playAnimation()
            binding.loading.visibility = View.VISIBLE
        }
    }
    private fun setAnimation() {
        binding.newsIcon.setAnimation("news.json")
        binding.newsIcon.loop(true)
        binding.newsIcon.playAnimation()
        binding.newsIcon.visibility = View.VISIBLE

        binding.questionIcon.setAnimation("question5.json")
        binding.questionIcon.loop(true)
        binding.questionIcon.playAnimation()
        binding.questionIcon.visibility = View.VISIBLE

        binding.listIcon.setAnimation("world1.json")
        binding.listIcon.loop(true)
        binding.listIcon.playAnimation()
        binding.listIcon.visibility = View.VISIBLE

        binding.logoutIcon.setAnimation("logout.json")
        binding.logoutIcon.loop(true)
        binding.logoutIcon.playAnimation()
        binding.logoutIcon.visibility = View.VISIBLE

        binding.updateIcon.setAnimation("downloading.json")
        binding.updateIcon.loop(true)
        binding.updateIcon.playAnimation()
        binding.updateIcon.visibility = View.VISIBLE
    }




    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }
}