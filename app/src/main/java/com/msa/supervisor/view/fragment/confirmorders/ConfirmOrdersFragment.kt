package com.msa.supervisor.view.fragment.confirmorders


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zar.core.enums.EnumApiError
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentConfirmOrdersBinding
import com.msa.supervisor.model.data.request.DataConfrimOrderReguest
import com.msa.supervisor.model.data.response.order.OrderConfirmModel
import com.msa.supervisor.tool.Convert_Number
import com.msa.supervisor.tool.customtoast.MsaToast
import com.msa.supervisor.tool.customtoast.MsaToastStyle
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.adapter.holder.OrderConfirmHolder
import com.msa.supervisor.view.adapter.recycler.OrderConfirmAdapter
import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import com.msa.supervisor.view.dialog.multiplechoice.MultipleChoiceDialog
import com.msa.supervisor.view.dialog.multiplechoice.SelectionCompleteListener
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class ConfirmOrdersFragment(override var layout: Int = R.layout.fragment_confirm_orders) :
    BaseFragment<FragmentConfirmOrdersBinding>() {

    private val viewModel: ConfirmOrdersViewModel by viewModels()
    private val visitorItems: MutableList<MultiItem> = ArrayList()
    private val selectVisitor: MutableList<String> = ArrayList()
    private val selectOrderStatus: MutableList<OrderConfirmModel> = ArrayList()
    private val REQUESTCODE = 100
    private lateinit var adapter: OrderConfirmAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListener()
        showBtnSendAll()
        observeLiveDate()
    }


    private fun initView() {
        viewModel.getVisitor()
    }

    private fun setListener() {
        binding.constraintLayoutVisitorTitle.setOnClickListener {
            setSelectVisitor()
        }

        binding.btnRequestOrder.setOnClickListener {
            viewModel.requestOrderConfirm(selectVisitor)
            lottieisAnimating(false)
        }


        binding.searchSearchbar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("SuspiciousIndentation")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)


            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty())
                    adapter.filter.filter(s)
            }

        })

        binding.searchVoice.setOnClickListener {
            startSpeechToText()
        }


        binding.fabSendAll.setOnClickListener {

            val orderNumbers: MutableList<String> = ArrayList()
            selectOrderStatus.forEach {
                orderNumbers.add(it.orderNumber.toString())
            }
            val data = DataConfrimOrderReguest(orderNumbers, "r2")
            viewModel.requestSendDataOrderConfirm(data)
            lottieisAnimating(false)
            selectOrderStatus.clear()
        }
    }

    private fun observeLiveDate() {

        viewModel.errorLiveDate.observe(viewLifecycleOwner) {
            lottieisAnimating(true)
            showMessage(it.message)
            when (it.type) {
                EnumApiError.UnAuthorization -> {
                    (activity as MainActivity?)?.gotoFirstFragment()
                }

                else -> {

                }
            }
        }

        viewModel.visitorItems.observe(viewLifecycleOwner) {
            visitorItems.clear()
            visitorItems.addAll(it)
        }

        viewModel.confirmOrder.observe(viewLifecycleOwner) {
            lottieisAnimating(true)
            showBtnSendAll()

            adapter = OrderConfirmAdapter(
                it as MutableList<OrderConfirmModel>,
                it,
                click
            )
            val layoutManagerVertical = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.recyclerOrder.adapter = adapter
            binding.recyclerOrder.layoutManager = layoutManagerVertical

        }
        viewModel.confirmOrderSend.observe(viewLifecycleOwner) {
            viewModel.requestOrderConfirm(selectVisitor)
        }
    }


    val click = object : OrderConfirmHolder.Click {
        override fun selectItem(item: OrderConfirmModel, sendData: Boolean) {
            Log.d(ContentValues.TAG, "selectItem: $item")
            if (sendData) {
                val data = DataConfrimOrderReguest(listOf(item.orderNumber), "r2")
                viewModel.requestSendDataOrderConfirm(data)
                lottieisAnimating(false)
            } else {
                val bundle = Bundle()
                bundle.putParcelable("item", item)
                val action = ConfirmOrdersFragmentDirections
                    .actionConfirmOrdersFragmentToOrderConfirmDetailsFragment(item)
                findNavController().navigate(action)
            }
        }

        override fun selectItemCheckBox(item: OrderConfirmModel, sendData: Boolean) {
            if (sendData)
                selectOrderStatus.add(item)
            else
                selectOrderStatus.remove(item)

            showBtnSendAll()
        }


    }

    private fun setSelectVisitor() {
        MultipleChoiceDialog.show(requireContext(),
            "لیست ویزیتورها",
            "تایید",
            visitorItems,
            object : SelectionCompleteListener {
                override fun onCompleteSelection(selectedItems: ArrayList<MultiItem>) {
                    Log.e("data", selectedItems.toString())
                    var text = ""
                    selectedItems.forEachIndexed { index, multiItem ->
                        text += multiItem.name
                        selectVisitor.add(index, multiItem.id)
                    }
                    binding.textViewShowVisitorDialog.text = text
                    binding.textViewShowVisitorDialog.isSelected = true
                }
            })
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

    @Deprecated("Deprecated in Java")
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

    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }


    fun showBtnSendAll() {

        if (selectOrderStatus.size > 0)
            binding.fabSendAll.visibility = View.VISIBLE
        else
            binding.fabSendAll.visibility = View.INVISIBLE
    }

}