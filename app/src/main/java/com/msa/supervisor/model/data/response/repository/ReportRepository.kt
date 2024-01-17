package com.msa.supervisor.model.data.response.repository

import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.dao.BranchesDao
import com.msa.supervisor.model.data.database.dao.ProductGroupDao
import com.msa.supervisor.model.data.request.OrderStatusRequestModel
import retrofit2.http.Query
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
class ReportRepository @Inject constructor(
    private val apiSupervisor: ApiSupervisor,
    private val productGroupDao: ProductGroupDao
) {

   suspend fun requestInvoiceRemain(dealersId: List<String?>?,
                             startDate: String?,
                             endDate: String?)= apiCall { apiSupervisor.invoiceRemain(dealersId,startDate,endDate) }




    suspend fun requestCustomerGroupSales(dealersId: List<String?>?,
                                          startDate: String?,
                                          endDate: String?)=
        apiCall { apiSupervisor.customerGroupSales(dealersId,startDate,endDate) }

    suspend fun requestProductSalesSummaryReport(dealersId: List<String?>?,
                                          startDate: String?,
                                          endDate: String?)=
        apiCall { apiSupervisor.productSalesSummaryReport(dealersId,startDate,endDate) }


    suspend fun requestCustomerNoSaleReport(dealersId: List<String?>?,
                                                 startDate: String?,
                                                 endDate: String?,
                                            productCategoriesId: List<String>?)=
        apiCall { apiSupervisor.customerNoSaleReport(dealersId,startDate,endDate) }

    suspend fun requestOrderStatusReport(param: OrderStatusRequestModel)=
        apiCall { apiSupervisor.requestOrderStatusReport(param) }


    suspend fun requestReturnReport(param: OrderStatusRequestModel)=
        apiCall { apiSupervisor.requestReturnReport(param) }

    fun getProductGroupId():List<String>{
        return productGroupDao.getProductGroupId()
    }


}