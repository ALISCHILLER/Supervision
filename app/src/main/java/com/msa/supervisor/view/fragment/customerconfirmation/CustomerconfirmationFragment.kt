package com.msa.supervisor.view.fragment.customerconfirmation


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentCustomerconfirmationBinding
import com.msa.supervisor.model.data.database.entity.CustomerapprovalEntity
import com.msa.supervisor.tool.Convert_Number
import com.msa.supervisor.tool.customtoast.MsaToast
import com.msa.supervisor.tool.customtoast.MsaToastStyle
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.adapter.holder.CustomerConfirmationHolder
import com.msa.supervisor.view.adapter.recycler.CustomerConfirmationAdapter
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by  Ali Soleymani.
 */
@AndroidEntryPoint
class CustomerconfirmationFragment(override var layout: Int = R.layout.fragment_customerconfirmation) :
    BaseFragment<FragmentCustomerconfirmationBinding>() {


    private val viewModel: CustomerconfirmationViewModel by viewModels()

    private lateinit var adapter: CustomerConfirmationAdapter
    private val REQUEST_CODE = 100


    val click = object : CustomerConfirmationHolder.Click {
        override fun selectItem(item: CustomerapprovalEntity, check: Boolean) {
            Log.d(TAG, "selectItem: $item")
            viewModel.requestSendCustomerapproval(item.uniqueId, check)
            lottieisAnimating(false)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initView()
        setListener()
        observeLiveDate()
    }

    private fun initView() {
        viewModel.getItem()
        viewModel.requestCustomerApproval()
        lottieisAnimating(false)
    }

    private fun setListener() {

        binding.textConfirminformation.setOnClickListener {
            viewModel.requestCustomerApproval()
            lottieisAnimating(false)
        }

        binding.searchSearchbar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchAllFields(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })


        binding.searchVoice.setOnClickListener {
            startSpeechToText()
        }


    }


    private fun observeLiveDate() {
        viewModel.errorLiveDate.observe(viewLifecycleOwner) {
            lottieisAnimating(true)
            showMessage(it.message)
            CoroutineScope(IO).launch {
                delay(2000)
            }

        }
        viewModel.customerapprovalLiveDate.observe(viewLifecycleOwner) {
            lottieisAnimating(true)
            adapter = CustomerConfirmationAdapter(
                click,
                it as MutableList<CustomerapprovalEntity>
            )
            val layoutManagerVertical = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.recylerCustomer.adapter = adapter
            binding.recylerCustomer.layoutManager = layoutManagerVertical
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
            startActivityForResult(intent, REQUEST_CODE)
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

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
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

    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }


}