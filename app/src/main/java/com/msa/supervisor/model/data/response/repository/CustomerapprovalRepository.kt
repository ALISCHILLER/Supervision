package com.msa.supervisor.model.data.response.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.dao.CustomerapprovalDao
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.model.data.database.entity.CustomerapprovalEntity
import java.util.UUID
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
class CustomerapprovalRepository@Inject constructor(
    private val apiSupervisor: ApiSupervisor,
    private val customerapprovalDao: CustomerapprovalDao
) {

    suspend fun requestCustomerapproval(supervisorId: String)
    = apiCall { apiSupervisor.getCustomerapproval(supervisorId) }
    suspend fun requestSendCustomerapproval(customerId: UUID,state:Boolean)
            = apiCall { apiSupervisor.requestSendCustomerapproval(customerId,state) }

    fun insertCustomerApproval(customerapprovalEntity: List<CustomerapprovalEntity>){
        customerapprovalDao.insertCustomerapproval(customerapprovalEntity)
    }

    fun getCustomerApproval():List<CustomerapprovalEntity>{
        return customerapprovalDao.getCustomerapproval()
    }


    fun searchAllFields(query: SupportSQLiteQuery):List<CustomerapprovalEntity>{
        return customerapprovalDao.searchAllFields(query)
    }

}