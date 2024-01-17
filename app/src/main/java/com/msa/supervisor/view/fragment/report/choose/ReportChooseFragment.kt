package com.msa.supervisor.view.fragment.report.choose

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.zar.core.enums.EnumApiError
import com.zar.core.tools.extensions.solarDateToGregorianDate
import com.zar.core.view.picker.date.customviews.DateRangeCalendarView
import com.zar.core.view.picker.date.dialog.DatePickerDialog
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentReportChooseBinding
import com.msa.supervisor.model.data.response.report.CustomerNoSaleReportModel
import com.msa.supervisor.model.data.response.report.CustomerSalesSummaryReportModel
import com.msa.supervisor.model.data.response.report.ItemReportModel
import com.msa.supervisor.model.data.response.report.OrderStatusReportModel
import com.msa.supervisor.model.data.response.report.ProductInvoiveBalanceReportModel
import com.msa.supervisor.model.data.response.report.ProductSalesSummaryReportModel
import com.msa.supervisor.model.data.response.report.ReturnDealerModel
import com.msa.supervisor.model.enum.EnumRequestReport
import com.msa.supervisor.tool.customtoast.MsaToast
import com.msa.supervisor.tool.customtoast.MsaToastStyle
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.adapter.holder.InvoiveBalanceHolder
import com.msa.supervisor.view.adapter.holder.TabItemReportHolder
import com.msa.supervisor.view.adapter.recycler.CustomerGroupSalesAdapter
import com.msa.supervisor.view.adapter.recycler.CustomerNoSaleAdapter
import com.msa.supervisor.view.adapter.recycler.InvoiveBalanceAdapter
import com.msa.supervisor.view.adapter.recycler.ProductSalesSummaryAdapter
import com.msa.supervisor.view.adapter.recycler.TabItemReportAdapter
import com.msa.supervisor.view.adapter.recycler.report.OrderStatusReportAdapter
import com.msa.supervisor.view.adapter.recycler.report.ReturnReportAdapter
import com.msa.supervisor.view.dialog.TotalReportDialog
import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import com.msa.supervisor.view.dialog.multiplechoice.MultipleChoiceDialog
import com.msa.supervisor.view.dialog.multiplechoice.SelectionCompleteListener
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter


/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class ReportChooseFragment(override var layout: Int = R.layout.fragment_report_choose) :
    BaseFragment<FragmentReportChooseBinding>() {

    private val viewModel: ReportChooseViewModel by viewModels()
    private val visitorItems: MutableList<MultiItem> = ArrayList()
    private val selectVisitor: MutableList<String> = ArrayList()
    private var startDate: String = ""
    private var endDate: String = ""
    private var date = ""
    private var delear = ""
    lateinit var adapter: TabItemReportAdapter
    private var type: EnumRequestReport = EnumRequestReport.InvoiceRemain

    private var invoiveBalanceReport: MutableList<ProductInvoiveBalanceReportModel> = ArrayList()

    private var customerGroupSalesReport: MutableList<CustomerSalesSummaryReportModel> = ArrayList()

    private var productSalesSummarReport: MutableList<ProductSalesSummaryReportModel> = ArrayList()

    private var customerNoSaleReport: MutableList<CustomerNoSaleReportModel> = ArrayList()

    private var orderStatusReportModel: MutableList<OrderStatusReportModel> = ArrayList()

    private var returnReport: MutableList<ReturnDealerModel> = ArrayList()

    //---------------------------------------------------------------------------------------------- onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveDate()
        setListener()
        initView()

    }

    val clickInvoive = object : InvoiveBalanceHolder.Click {
        override fun selectItem(item: ProductInvoiveBalanceReportModel) {
            Log.e("TAG", "selectItem: $item ")
        }
    }


    //---------------------------------------------------------------------------------------------- onViewCreated
    private fun initView() {
        val click = object : TabItemReportHolder.Click {
            override fun selectItem(item: ItemReportModel, position: Int) {
                Log.e("TAG", "selectItem: ${item.type} ")
                typeReport(item.type)
                type = item.type
                visiblityItem()
                adapter.selectPosition = position
                adapter.notifyItemRangeChanged(0, adapter.itemCount)
            }
        }

        viewModel.getVisitor()
        adapter = TabItemReportAdapter(viewModel.getTabItem(), click, requireContext())
        adapter.selectPosition = 0

        val layoutManagerVertical = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )


        val linearLayoutManagerHorizontal = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            true
        )
        binding.recyclerViewReport.layoutManager = layoutManagerVertical
        binding.recyclerTabItemReport.layoutManager = linearLayoutManagerHorizontal
        binding.recyclerTabItemReport.adapter = adapter


    }

    private fun setSelectVisitor() {
        MultipleChoiceDialog.show(requireContext(),
            "لیست ویزیتورها",
            "تایید",
            visitorItems,
            object : SelectionCompleteListener {
                override fun onCompleteSelection(selectedItems: ArrayList<MultiItem>) {
                    Log.e("data", selectedItems.toString())
                    delear=""
                    selectVisitor.clear()
                    selectedItems.forEachIndexed { index, multiItem ->
                        if (index == 0)
                            delear += multiItem.name
                        else
                            delear += "-" + multiItem.name
                        selectVisitor.add(index, multiItem.id)
                    }
                    binding.textViewShowVisitorSelected.text = delear
                    binding.textViewShowVisitorSelected.isSelected = true
                }
            })
    }

    //---------------------------------------------------------------------------------------------- showMessage
    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }
    //---------------------------------------------------------------------------------------------- showMessage


    //---------------------------------------------------------------------------------------------- observeLiveDate
    private fun observeLiveDate() {
        viewModel.errorLiveDate.observe(viewLifecycleOwner) {
            lottieisAnimating(true)
            showMessage(it.message)
            when (it.type) {
                EnumApiError.UnAuthorization -> (activity as MainActivity?)?.gotoFirstFragment()
                else -> {}
            }
        }

        viewModel.markersVisitor.observe(viewLifecycleOwner) {
            it.forEachIndexed { index, markersVisitor ->
                val visitor = MultiItem(
                    markersVisitor.nameVisitor.toString(),
                    markersVisitor.visitorId.toString(),
                    ""
                )
                visitorItems.add(index, visitor)
            }
        }
        viewModel.invoiveBalanceReport.observe(viewLifecycleOwner) {
            invoiveBalanceReport = it as MutableList<ProductInvoiveBalanceReportModel>
            typeReport(type)
            lottieisAnimating(true)
        }

        viewModel.customerGroupSalesReport.observe(viewLifecycleOwner) {
            customerGroupSalesReport = it as MutableList<CustomerSalesSummaryReportModel>
            typeReport(type)
            lottieisAnimating(true)
        }

        viewModel.productSalesSummaryReport.observe(viewLifecycleOwner) {
            productSalesSummarReport = it as MutableList<ProductSalesSummaryReportModel>
            typeReport(type)
            lottieisAnimating(true)
        }
        viewModel.customerNoSaleReportModelReport.observe(viewLifecycleOwner) {

            customerNoSaleReport = it as MutableList<CustomerNoSaleReportModel>
            typeReport(type)
            lottieisAnimating(true)
        }

        viewModel.orderStatusReportModel.observe(viewLifecycleOwner) {
            orderStatusReportModel = it as MutableList<OrderStatusReportModel>
            typeReport(type)
            lottieisAnimating(true)
        }


        viewModel.returnReport.observe(viewLifecycleOwner) {
            returnReport = it as MutableList<ReturnDealerModel>
            typeReport(type)
            lottieisAnimating(true)
        }

    }
    //---------------------------------------------------------------------------------------------- observeLiveDate


    private fun typeReport(type: EnumRequestReport) {
        when (type) {
            EnumRequestReport.InvoiceRemain -> showInvoiceRemainReport()
            EnumRequestReport.CustomerGroupSales -> showCustomerGroupSalesReport()
            EnumRequestReport.ProductSalesSummaryReport -> showProductSalesSummaryReport()
            EnumRequestReport.CustomerNoSaleReport -> showCustomerNoSaleReport()
            EnumRequestReport.OrderStatusReport -> showOrderStatusReport()
            EnumRequestReport.ReturnReport -> showReturnReport()
        }
    }

    private fun showInvoiceRemainReport() {
        val adapter = InvoiveBalanceAdapter(invoiveBalanceReport, clickInvoive)
        binding.recyclerViewReport.adapter = adapter

        binding.layoutFooterRight.setBackgroundResource(R.drawable.drawable_report_curve_white)
        binding.layoutFooterLeft.setBackgroundResource(R.drawable.drawable_report_curve_white)
        binding.txtFooterLeft.text = getString(R.string.totalDisplay)
        binding.txtFooterRight.text = getString(R.string.downloadTheExcelFile)
    }

    private fun showCustomerGroupSalesReport() {
        val adapter = CustomerGroupSalesAdapter(customerGroupSalesReport)
        binding.recyclerViewReport.adapter = adapter
        val fullNetWeight =
            viewModel.getFullCustomerSalesSummary(customerGroupSalesReport) { it.netWeight }
        binding.txtFooterRight.text = resources.getString(R.string.fullnetWeight, fullNetWeight)
        val totalNetWeight =
            viewModel.getFullCustomerSalesSummary(customerGroupSalesReport) { it.netCount_CA }
        binding.txtFooterLeft.text = getString(R.string.totalNetWeight, totalNetWeight)
        binding.layoutFooterRight.setBackgroundResource(R.drawable.drawable_rectangle_red)
        binding.layoutFooterLeft.setBackgroundResource(R.drawable.drawable_rectangle_red)
    }

    private fun showProductSalesSummaryReport() {
        val adapter = ProductSalesSummaryAdapter(productSalesSummarReport)
        binding.recyclerViewReport.adapter = adapter
        val fullNetWeight =
            viewModel.getFullProductSalesSummaryReport(productSalesSummarReport) { it.productNetWeight }




        val totalNetWeight =
            viewModel.getFullProductSalesSummaryReportInt(productSalesSummarReport) {
                it.productNetCount_CA
            }
        binding.txtFooterRight.text = resources.getString(R.string.fullnetWeight, totalNetWeight)
        binding.txtFooterLeft.text = getString(R.string.totalNetWeight, fullNetWeight)
        binding.layoutFooterRight.setBackgroundResource(R.drawable.drawable_rectangle_red)
        binding.layoutFooterLeft.setBackgroundResource(R.drawable.drawable_rectangle_red)
    }

    private fun showCustomerNoSaleReport() {
        val adapter = CustomerNoSaleAdapter(customerNoSaleReport)
        binding.recyclerViewReport.adapter = adapter
    }


    private fun showOrderStatusReport() {
        val adapter = OrderStatusReportAdapter(orderStatusReportModel, requireContext())
        val layoutManagerVertical = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerViewReportEx.layoutManager = layoutManagerVertical
        binding.recyclerViewReportEx.adapter = adapter
    }

    private fun showReturnReport() {
        val adapter = ReturnReportAdapter(returnReport, requireContext())
        val layoutManagerFlexbox = FlexboxLayoutManager(requireActivity())
        layoutManagerFlexbox.flexDirection = FlexDirection.ROW_REVERSE
        layoutManagerFlexbox.justifyContent = JustifyContent.FLEX_START
        val layoutManagerVertical = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerViewReport.layoutManager = layoutManagerVertical
        binding.recyclerViewReport.adapter = adapter

    }

    //---------------------------------------------------------------------------------------------- setListener
    private fun setListener() {

        binding.textViewShowVisitorDialog.setOnClickListener {
            setSelectVisitor()

        }
        binding.textViewDateTo.isEnabled = false
        binding.textViewDateFrom.setOnClickListener {
            showDatePickerDialog(true)
            binding.textViewDateTo.isEnabled = true
        }

        binding.textViewDateTo.setOnClickListener {
            showDatePickerDialog(false)
        }
        binding.btnconfirmdata.setOnClickListener {
            if (delear.isNotEmpty() && date.isNotEmpty()) {
                lottieisAnimating(false)
                viewModel.reqouest(type, selectVisitor, startDate, endDate)
            } else
                shoeToest("لطفا تاریخ و ویزیتور انتخاب کنید")
        }
        binding.layoutFooterLeft.setOnClickListener {
            if (invoiveBalanceReport.size > 0&&type.equals(EnumRequestReport.InvoiceRemain))
                TotalReportDialog.show(
                    requireContext(),
                    date,
                    delear,
                    invoiveBalanceReport
                )
            else
                shoeToest("اطلاعاتی در دسرس نیست")
        }
    }

    //---------------------------------------------------------------------------------------------- setListener


    fun visiblityItem() {
        when (type) {
            EnumRequestReport.CustomerNoSaleReport -> {
                binding.layoutFooter.visibility = View.INVISIBLE
                binding.scrollable.visibility = View.GONE
                binding.recyclerViewReportEx.visibility = View.GONE
            }

            EnumRequestReport.OrderStatusReport -> {
                binding.layoutFooter.visibility = View.INVISIBLE
                binding.scrollable.visibility = View.VISIBLE
                binding.recyclerViewReport.visibility = View.INVISIBLE
                binding.recyclerViewReportEx.visibility = View.VISIBLE
            }
            EnumRequestReport.ReturnReport -> {
                binding.recyclerViewReport.visibility = View.VISIBLE
                binding.scrollable.visibility = View.GONE
                binding.layoutFooter.visibility = View.INVISIBLE
            }
            else -> {
                binding.layoutFooter.visibility = View.VISIBLE
                binding.recyclerViewReport.visibility = View.VISIBLE
                binding.scrollable.visibility = View.GONE
            }
        }
    }

    private fun lottieisAnimating(runAn: Boolean) {
        if (binding.loadingreport.isAnimating || runAn) {
            binding.loadingreport.pauseAnimation()
            binding.loadingreport.cancelAnimation()
            binding.loadingreport.visibility = View.GONE
        } else {
            binding.loadingreport.setAnimation("report1.json")
            binding.loadingreport.loop(true)
            binding.loadingreport.playAnimation()
            binding.loadingreport.visibility = View.VISIBLE
        }
    }

    //---------------------------------------------------------------------------------------------- showDatePickerDialog
    private fun showDatePickerDialog(check: Boolean) {
        if (context == null)
            return
        val datePickerDialog = DatePickerDialog(requireContext())
        datePickerDialog.selectionMode = DateRangeCalendarView.SelectionMode.Single
        datePickerDialog.isDisableDaysAgo = false
        datePickerDialog.acceptButtonColor =
            resources.getColor(R.color.datePickerConfirmButtonBackColor, requireContext().theme)
        datePickerDialog.headerBackgroundColor =
            resources.getColor(R.color.datePickerConfirmButtonBackColor, requireContext().theme)
        datePickerDialog.headerTextColor =
            resources.getColor(R.color.white, requireContext().theme)
        datePickerDialog.weekColor =
            resources.getColor(R.color.textHint, requireContext().theme)
        datePickerDialog.disableDateColor =
            resources.getColor(R.color.textHint, requireContext().theme)
        datePickerDialog.defaultDateColor =
            resources.getColor(R.color.datePickerDateBackColor, requireContext().theme)
        datePickerDialog.selectedDateCircleColor =
            resources.getColor(R.color.datePickerConfirmButtonBackColor, requireContext().theme)
        datePickerDialog.selectedDateColor =
            resources.getColor(R.color.white, requireContext().theme)
        datePickerDialog.rangeDateColor =
            resources.getColor(R.color.datePickerConfirmButtonBackColor, requireContext().theme)
        datePickerDialog.rangeStripColor =
            resources.getColor(R.color.datePickerRangeColor, requireContext().theme)
        datePickerDialog.holidayColor =
            resources.getColor(R.color.datePickerHoliday, requireContext().theme)
        datePickerDialog.textSizeWeek = 12.0f
        datePickerDialog.textSizeDate = 14.0f
        datePickerDialog.textSizeTitle = 18.0f
        datePickerDialog.setCanceledOnTouchOutside(true)
        datePickerDialog.onSingleDateSelectedListener =
            DatePickerDialog.OnSingleDateSelectedListener {

                if (check) {
                    date = it.persianShortDate
                    it.persianShortDate.solarDateToGregorianDate()
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    startDate = it.persianShortDate
                    binding.textViewDateSelected.text = date
                } else {
                    date += " تا${it.persianShortDate}"
                    it.persianShortDate.solarDateToGregorianDate()
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    endDate = it.persianShortDate
                    binding.textViewDateSelected.text = date
                }
            }
//        datePickerDialog.onRangeDateSelectedListener =
//            DatePickerDialog.OnRangeDateSelectedListener { _, _ -> }

        datePickerDialog.showDialog()

    }
//---------------------------------------------------------------------------------------------- showDatePickerDialog

    private fun shoeToest(message: String) {
        MsaToast.darkColorToast(
            requireActivity(),
            "خطا ☹️",
            "$message !",
            MsaToastStyle.ERROR,
            MsaToast.GRAVITY_BOTTOM,
            MsaToast.LONG_DURATION,
            ResourcesCompat.getFont(requireActivity(), R.font.mitra_bold),
            requireContext().getColor(R.color.white)
        )
    }
}