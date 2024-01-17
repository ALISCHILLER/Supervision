package com.msa.supervisor.view.fragment.questionnaire

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msa.supervisor.view.dialog.multiplechoice.SingleSelectionCompleteListener
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentCustomersQuestionnaireBinding
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.tool.Convert_Number
import com.msa.supervisor.tool.customtoast.MsaToast
import com.msa.supervisor.tool.customtoast.MsaToastStyle
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.adapter.holder.CustomersQuestionnaireHolder
import com.msa.supervisor.view.adapter.recycler.CustomersQuestionnaireAdapter
import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import com.msa.supervisor.view.dialog.multiplechoice.SingleChoiceDialog
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class CustomersQuestionnaireFragment(override var layout: Int = R.layout.fragment_customers_questionnaire) :
    BaseFragment<FragmentCustomersQuestionnaireBinding>() {
    private val viewModel: QuestionnaireViewModel by viewModels()
    lateinit var adapter: CustomersQuestionnaireAdapter
    private val listQuestionnaireItems: MutableList<MultiItem> = ArrayList()
    private var questionnaireId: MultiItem? = null
    private val REQUESTCODE = 100
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveDate()
        setListener()
        initView()
    }


    private fun initView() {
        viewModel.getCustomer()

        val linearLayoutManagerHorizontal = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerCustomer.layoutManager = linearLayoutManagerHorizontal
    }

    private fun setListener() {

        binding.searchSearchbar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getSearchCustomer(s.toString())
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
            showMessage(it.message)
        }

        viewModel.customersQuestionnaire.observe(viewLifecycleOwner) {

            adapter = CustomersQuestionnaireAdapter(
                items = it as MutableList<CustomerEntity>,
                click, requireContext()
            )
            binding.recyclerCustomer.adapter = adapter
        }

        viewModel.questionnairesHeadr.observe(viewLifecycleOwner) {
            listQuestionnaireItems.clear()
            listQuestionnaireItems.addAll(it)
            singelDialog()
        }

        viewModel.questionnairesHistoryValid.observe(viewLifecycleOwner) {
            if (!it) {
                val action = CustomersQuestionnaireFragmentDirections
                    .actionCustomersQuestionnaireFragmentToQuestionnaireFormFragment(
                        questionnaireId?.customer,
                        questionnaireId?.id
                    )
                findNavController().navigate(action)
            } else
                MsaToast.darkColorToast(
                    requireActivity(),
                    "",
                    "برای این مشتری قبلا پرسشنامه پر شده است",
                    MsaToastStyle.INFO,
                    MsaToast.GRAVITY_BOTTOM,
                    MsaToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(), R.font.mitra_bold),
                    requireContext().getColor(R.color.white)
                )
        }
    }

    val click = object : CustomersQuestionnaireHolder.Click {
        override fun selectItem(item: CustomerEntity) {
            Log.e("TAG", "CustomersQuestionnaireHolder: $item ")
            viewModel.getQuestionnaireHeader(item)
        }

    }

    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }


    private fun singelDialog() {
        SingleChoiceDialog.show(
            requireContext(),
            "پرسشنامه",
            R.drawable.ic_notes_white,
            listQuestionnaireItems,
            object : SingleSelectionCompleteListener {
                override fun onCompleteSelection(selectedItem: MultiItem) {
                    Log.e(ContentValues.TAG, "onCompleteSelection: $selectedItem")
                    viewModel.getQuestionnaireHistory(selectedItem.id, selectedItem.customer)
                    questionnaireId = selectedItem
                }
            }
        )
    }

    private fun startSpeechToText() {
        val language = "fa-IR"
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language)
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, language)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "صحبت کنید")
        }

        try {
            startActivityForResult(intent, REQUESTCODE)
        } catch (e: ActivityNotFoundException) {

            MsaToast.darkColorToast(requireActivity(),
                "خطا ☹️",
                "تشخیص گفتار در این دستگاه پشتیبانی نمی شود !",
                MsaToastStyle.ERROR,
                MsaToast.GRAVITY_BOTTOM,
                MsaToast.LONG_DURATION,
                ResourcesCompat.getFont(requireActivity(),R.font.mitra_bold),
                requireContext().getColor(R.color.white)
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUESTCODE && resultCode == Activity.RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val converter = Convert_Number()
            val recognizedText :String = converter.PersianToEnglish(result?.get(0).toString())
            binding.searchSearchbar.setText(recognizedText)
            // Do something with the recognized text
          //  Toast.makeText(requireContext(), "You said: $recognizedText", Toast.LENGTH_SHORT).show()
        }
    }



}