package com.msa.supervisor.view.fragment.questionnaire

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentQuestionnaireFormBinding
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineOptionEntity
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireLineModel
import com.msa.supervisor.tool.customtoast.MsaToast
import com.msa.supervisor.tool.customtoast.MsaToastStyle
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.adapter.recycler.questionnaireFrom.QuestionnaireFromAdapter
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class QuestionnaireFormFragment(override var layout: Int = R.layout.fragment_questionnaire_form) :
    BaseFragment<FragmentQuestionnaireFormBinding>() {
    private val viewModel: QuestionnaireViewModel by viewModels()
    private val args:QuestionnaireFormFragmentArgs by navArgs()
    private lateinit var customerId:String
    private lateinit var questionnaireId:String
    private  var questionnaireLine:MutableList<QuestionnaireLineModel> = ArrayList()

    private var currentPosition: Int = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveDate()
        setListener()
        initView()
    }

    private fun initView() {
        customerId= args.customerId.toString()
        questionnaireId= args.questionnaireId.toString()
        viewModel.getQuestionnaireLine(questionnaireId)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setListener() {
        binding.materialButtonNext.setOnClickListener {
            val current = (binding.recyclerViewQuestion.layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()
            if (viewModel.checkValidationAnswerByIndex(current)) {
                binding.recyclerViewQuestion.smoothScrollToPosition(current + 1)
            } else
                MsaToast.darkColorToast(requireActivity(),
                    "خطا ☹️",
                    "پاسخ به سوال اجباریست",
                    MsaToastStyle.WARNING,
                    MsaToast.GRAVITY_BOTTOM,
                    MsaToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireContext(),R.font.mitra_bold),
                    requireContext().getColor(R.color.white)
                )


        }

        binding.materialButtonPrevious.setOnClickListener {
            val current = (binding.recyclerViewQuestion.layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()
            if(current>0)
            binding.recyclerViewQuestion.smoothScrollToPosition(current - 1)

        }

        binding.btnSave.setOnClickListener {
            viewModel.sendQuestionnaire(customerId,questionnaireId)
            lottieisAnimating(false)

        }
    }

    private fun observeLiveDate() {
        viewModel.errorLiveDate.observe(viewLifecycleOwner) {
            showMessage(it.message)
            lottieisAnimating(true)
        }

        viewModel.sendQuestionnaire.observe(viewLifecycleOwner){
            lottieisAnimating(true)
            findNavController().navigate(R.id.action_questionnaireFormFragment_to_customersQuestionnaireFragment)
        }
        viewModel.questionnaireLine.observe(viewLifecycleOwner) {
            questionnaireLine.addAll(it)
        }

        viewModel.questionnaireLineOption.observe(viewLifecycleOwner){
            setQuestionsSlider(it)
        }

    }


    private fun setQuestionsSlider(questionsOption: List<QuestionnaireLineOptionEntity>) {
        currentPosition = 0
        val adapter = QuestionnaireFromAdapter(questionnaireLine,questionsOption,requireActivity())
        startSurvey()
        binding.recyclerViewQuestion.adapter=adapter
        val manager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.recyclerViewQuestion.layoutManager = manager

        val snapHelper: SnapHelper = PagerSnapHelper()
        binding.recyclerViewQuestion.adapter = adapter
        snapHelper.attachToRecyclerView(binding.recyclerViewQuestion)
        binding.recyclerViewQuestion.addOnScrollListener(getRecyclerViewQuestionScrollListener())

    }
    private fun getRecyclerViewQuestionScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val count = (binding.recyclerViewQuestion.layoutManager as LinearLayoutManager)
                .itemCount
            val current = (binding.recyclerViewQuestion.layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()
            when (current) {
                0 -> {
                    binding.btnSave.visibility = View.GONE
                    binding.materialButtonPrevious.visibility = View.GONE
                    binding.materialButtonNext.visibility = View.VISIBLE
                }
                count - 1 -> {
                    binding.btnSave.visibility = View.VISIBLE
                    binding.materialButtonPrevious.visibility = View.VISIBLE
                    binding.materialButtonNext.visibility = View.GONE
                }
                else -> {
                    binding.btnSave.visibility = View.GONE
                    binding.materialButtonPrevious.visibility = View.VISIBLE
                    binding.materialButtonNext.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun startSurvey() {
        binding.btnSave.visibility = View.GONE
        binding.materialButtonPrevious.visibility = View.GONE
        binding.expandableQuestions.expand()
    }
    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }    }
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