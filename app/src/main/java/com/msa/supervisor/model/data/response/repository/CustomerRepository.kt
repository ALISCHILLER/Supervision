package com.msa.supervisor.model.data.response.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.dao.CustomerDao
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
class CustomerRepository @Inject constructor(
    private val apiSupervisor: ApiSupervisor,
    private val customerDao: CustomerDao
) {

    suspend fun requestCustomerAll(dealersId: List<String>)=
        apiCall {apiSupervisor.getsupervisorCustomers(dealersId) }

    suspend fun requestCustomerPinCodes()=
        apiCall {apiSupervisor.getPinCodes()}

    fun insertCustomerAll(customer:List<CustomerEntity>){
        customerDao.insertCustomer(customer)
    }

    fun getCustomerAll():List<CustomerEntity>{
        return customerDao.getCustomer()
    }


    fun searchAllFields(query: SupportSQLiteQuery):List<CustomerEntity>{
        return customerDao.searchAllFields(query)
    }
}