package com.msa.supervisor.view.fragment.report.choose

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.msa.supervisor.model.data.request.OrderStatusRequestModel
import com.msa.supervisor.model.data.response.map.MarkersVisitor
import com.msa.supervisor.model.data.response.report.CustomerNoSaleReportModel
import com.msa.supervisor.model.data.response.report.CustomerSalesSummaryReportModel
import com.msa.supervisor.model.data.response.report.ItemReportModel
import com.msa.supervisor.model.data.response.report.OrderStatusReportModel
import com.msa.supervisor.model.data.response.report.ProductInvoiveBalanceReportModel
import com.msa.supervisor.model.data.response.report.ProductSalesSummaryReportModel
import com.msa.supervisor.model.data.response.report.ReturnDealerModel
import com.msa.supervisor.model.data.response.repository.ReportRepository
import com.msa.supervisor.model.data.response.repository.VisitorRepository
import com.msa.supervisor.model.enum.EnumRequestReport
import com.msa.supervisor.model.mapper.MapperMarkersVisitor
import com.msa.supervisor.tool.Currency
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject


/**
 * create by m-latifi on 4/16/2023
 */

@HiltViewModel
class ReportChooseViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val visitorRepository: VisitorRepository,
) :
    BaseViewModel() {
    val invoiveBalanceReport: com.msa.supervisor.tool.SingleLiveEvent<List<ProductInvoiveBalanceReportModel>> by
    lazy { com.msa.supervisor.tool.SingleLiveEvent<List<ProductInvoiveBalanceReportModel>>() }

    val customerGroupSalesReport: com.msa.supervisor.tool.SingleLiveEvent<List<CustomerSalesSummaryReportModel>> by
    lazy { com.msa.supervisor.tool.SingleLiveEvent<List<CustomerSalesSummaryReportModel>>() }

    val productSalesSummaryReport: MutableLiveData<List<ProductSalesSummaryReportModel>> by
    lazy { MutableLiveData<List<ProductSalesSummaryReportModel>>() }

    val customerNoSaleReportModelReport: MutableLiveData<List<CustomerNoSaleReportModel>> by
    lazy { MutableLiveData<List<CustomerNoSaleReportModel>>() }

    val orderStatusReportModel: MutableLiveData<List<OrderStatusReportModel>> by
    lazy { MutableLiveData<List<OrderStatusReportModel>>() }

    val returnReport: MutableLiveData<List<ReturnDealerModel>> by
    lazy { MutableLiveData<List<ReturnDealerModel>>() }


    val markersVisitor: MutableLiveData<List<MarkersVisitor>> by lazy { MutableLiveData<List<MarkersVisitor>>() }


    fun getVisitor() {
        viewModelScope.launch(IO + exceptionHandler()) {
            val visitorEntity = visitorRepository.getVisitors()
            val mapper = MapperMarkersVisitor()
            mapper.transformToDomain(visitorEntity).let {
                markersVisitor.postValue(it)
            }
        }
    }

    fun getTabItem(): MutableList<ItemReportModel> {
        val items: MutableList<ItemReportModel> = mutableListOf()

        items.add(ItemReportModel(EnumRequestReport.InvoiceRemain, "گزارش مانده فاکتور"))
        items.add(
            ItemReportModel(
                EnumRequestReport.CustomerGroupSales,
                "گزارش خلاصه  فروش گروه مشتری"
            )
        )
        items.add(
            ItemReportModel
                (EnumRequestReport.ProductSalesSummaryReport, "گزارش خلاصه فروش کالا")
        )
        items.add(ItemReportModel(EnumRequestReport.CustomerNoSaleReport, "مشتریان بدون خرید"))
        items.add(ItemReportModel(EnumRequestReport.OrderStatusReport, "گزارش وضعیت سفارش ها"))
        items.add(ItemReportModel(EnumRequestReport.ReturnReport, "گزارش برگشتی"))

        return items
    }

    fun getFullCustomerSalesSummary(
        customerSales: MutableList<CustomerSalesSummaryReportModel>,
        calculateTotal: (CustomerSalesSummaryReportModel) -> BigDecimal
    ): String {
        var total = BigDecimal.ZERO
        for (item in customerSales) {
            total = total.add(calculateTotal(item))
        }
        return Currency(total).toFormattedString()
    }

    fun getFullProductSalesSummaryReport(
        customerSales: MutableList<ProductSalesSummaryReportModel>,
        calculateTotal: (ProductSalesSummaryReportModel) -> BigDecimal
    ): String {
        var total = BigDecimal.ZERO
        for (item in customerSales) {
            total = total.add(calculateTotal(item))
        }
        return Currency(total).toFormattedString()
    }


    fun getFullProductSalesSummaryReportInt(
        customerSales: MutableList<ProductSalesSummaryReportModel>,
        calculateTotal: (ProductSalesSummaryReportModel) -> BigDecimal
    ): String {
        var total = BigDecimal.ZERO
        for (item in customerSales) {
            total = total.add(calculateTotal(item))
        }
        return total.toInt().toString()
    }
    fun reqouest(
        type: EnumRequestReport, dealersId: List<String?>,
        startDate: String?,
        endDate: String?
    ) = when (type) {
        EnumRequestReport.InvoiceRemain -> reqouestinvoiceRemain(dealersId, startDate, endDate)
        EnumRequestReport.CustomerGroupSales -> requestCustomerGroupSales(
            dealersId,
            startDate,
            endDate
        )

        EnumRequestReport.ProductSalesSummaryReport -> requestProductSalesSummaryReport(
            dealersId,
            startDate,
            endDate
        )

        EnumRequestReport.CustomerNoSaleReport -> requestCustomerNoSaleReport(
            dealersId,
            startDate,
            endDate
        )

        EnumRequestReport.OrderStatusReport -> requestOrderStatusReport(
            dealersId,
            startDate,
            endDate
        )

        EnumRequestReport.ReturnReport -> requestReturnReport(
            dealersId,
            startDate,
            endDate
        )
    }

    private fun reqouestinvoiceRemain(
        dealersId: List<String?>,
        startDate: String?,
        endDate: String?
    ) {
        viewModelScope.launch(IO + exceptionHandler()) {

            val response =
                callApi2(reportRepository.requestInvoiceRemain(dealersId, startDate, endDate))
            response?.let {
                Log.e("reqouestinvoiceRemain", "$it ")
                delay(3000)
                invoiveBalanceReport.postValue(it)
            }
        }
    }

    private fun requestCustomerGroupSales(
        dealersId: List<String?>,
        startDate: String?,
        endDate: String?
    ) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val response =
                callApi2(reportRepository.requestCustomerGroupSales(dealersId, startDate, endDate))
            response?.let {
                Log.e("reqouestinvoiceRemain", "$it ")
                delay(2000)
                customerGroupSalesReport.postValue(it)
            }
        }
    }

    private fun requestProductSalesSummaryReport(
        dealersId: List<String?>,
        startDate: String?,
        endDate: String?
    ) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val response = callApi2(
                reportRepository.requestProductSalesSummaryReport(
                    dealersId,
                    startDate,
                    endDate
                )
            )
            response?.let {
                Log.e("reqouestinvoiceRemain", "$it ")
                delay(2000)
                productSalesSummaryReport.postValue(it)
            }
        }
    }

    private fun requestCustomerNoSaleReport(
        dealersId: List<String?>,
        startDate: String?,
        endDate: String?,
    ) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val productId = reportRepository.getProductGroupId()
            productId[1]
            val response = callApi2(
                reportRepository.requestCustomerNoSaleReport(
                    dealersId,
                    startDate,
                    endDate,
                    listOf("")
                )
            )
            response?.let {
                Log.e("reqouestinvoiceRemain", "$it ")
                delay(2000)
                customerNoSaleReportModelReport.postValue(it)
            }
        }
    }

    private fun requestOrderStatusReport(
        dealersId: List<String?>,
        startDate: String?,
        endDate: String?,
    ) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val response = callApi2(
                reportRepository.requestOrderStatusReport(
                    OrderStatusRequestModel(
                        dealersId,
                        startDate,
                        endDate
                    )
                )
            )
            response?.let { it ->
                Log.e(TAG, "requestOrderStatusReport: $it")
                delay(2000)
                it.sortedWith(compareByDescending{it.date})
                orderStatusReportModel.postValue(it)
            }
        }
    }


    private fun requestReturnReport(
        dealersId: List<String?>,
        startDate: String?,
        endDate: String?,
    ) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val response = callApi2(
                reportRepository.requestReturnReport(
                    OrderStatusRequestModel(
                        dealersId,
                        startDate,
                        endDate
                    )
                )
            )
            response?.let {
                Log.e(TAG, "requestOrderStatusReport: $it")
                delay(2000)
                returnReport.postValue(it)
            }
        }
    }

}