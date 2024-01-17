package com.msa.supervisor.view.fragment.pincode


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zar.core.enums.EnumApiError
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentListPinCodeBinding
import com.msa.supervisor.model.data.response.customer.CustomerPinModel
import com.msa.supervisor.tool.Convert_Number
import com.msa.supervisor.tool.customtoast.MsaToast
import com.msa.supervisor.tool.customtoast.MsaToastStyle
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.adapter.recycler.ListPinCodeAdapter
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class ListPinCodeFragment(override var layout: Int = R.layout.fragment_list_pin_code) :
    BaseFragment<FragmentListPinCodeBinding>() {

    private val viewModel: PinCodeViewModel by viewModels()
    private var adapter: ListPinCodeAdapter? = null
    private val REQUESTCODE = 100
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeLiveDate()
        setAnimation()
        setListener()
    }

    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }


    private fun initView() {
        viewModel.requestPinCode()
        lottieisAnimating(false)
    }

    private fun observeLiveDate() {
        viewModel.errorLiveDate.observe(viewLifecycleOwner) {
            showMessage(it.message)
            lottieisAnimating(true)
            when (it.type) {
                EnumApiError.UnAuthorization -> (activity as MainActivity?)?.gotoFirstFragment()
                else -> {}
            }
        }

        viewModel.listPinCode.observe(viewLifecycleOwner) {

            adapter = ListPinCodeAdapter(it as MutableList<CustomerPinModel>, it)
            binding.recylerPin.adapter = adapter
            lottieisAnimating(true)
            val layoutManagerVertical = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.recylerPin.layoutManager = layoutManagerVertical
        }
    }

    fun setAnimation() {

    }

    private fun setListener() {
        binding.textConfirminformation.setOnClickListener {
            viewModel.requestPinCode()
            lottieisAnimating(false)
        }
        binding.searchSearchbar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("SuspiciousIndentation")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (adapter?.itemCount != null)
                    adapter?.filter?.filter(s)


            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty())
                    adapter?.filter?.filter(s)
            }

        })

        binding.searchVoice.setOnClickListener {
            startSpeechToText()
        }

    }


    private fun startSpeechToText() {
        val language = "fa-IR"
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language)
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, language)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "صحبت کنید")
        }

        try {
            startActivityForResult(intent, REQUESTCODE)
        } catch (e: ActivityNotFoundException) {
            MsaToast.darkColorToast(
                requireActivity(),
                "خطا ☹️",
                "تشخیص گفتار در این دستگاه پشتیبانی نمی شود !",
                MsaToastStyle.ERROR,
                MsaToast.GRAVITY_BOTTOM,
                MsaToast.LONG_DURATION,
                ResourcesCompat.getFont(requireActivity(), R.font.mitra_bold),
                requireContext().getColor(R.color.white)
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUESTCODE && resultCode == Activity.RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val converter = Convert_Number()
            val recognizedText: String = converter.PersianToEnglish(result?.get(0).toString())
            binding.searchSearchbar.setText(recognizedText)
            // Do something with the recognized text
            //  Toast.makeText(requireContext(), "You said: $recognizedText", Toast.LENGTH_SHORT).show()
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