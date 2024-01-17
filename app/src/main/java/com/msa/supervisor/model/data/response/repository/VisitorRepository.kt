package com.msa.supervisor.model.data.response.repository

import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.dao.VisitorsDao
import com.msa.supervisor.model.data.database.entity.VisitorsEntity
import com.msa.supervisor.model.data.request.VisitorVisitInfoRequest
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
class VisitorRepository @Inject constructor(
    private val apiSupervisor: ApiSupervisor,
    private val visitorDao: VisitorsDao
){


    suspend fun requestVisitor(supervisorId: String,type:String)=
        apiCall {apiSupervisor.getVisitors(supervisorId,type) }
    suspend fun requestVisitorInfo(visitInfoRequest:VisitorVisitInfoRequest)=
        apiCall {apiSupervisor.getVisitorsVisitInfo(visitInfoRequest) }
    fun insertData(VisitorEntity:List<VisitorsEntity>){
        visitorDao.insert(VisitorEntity)
    }

    fun getVisitors():List<VisitorsEntity>{
        return visitorDao.getVisitors()
    }

    fun getVisitorsId():List<String>{
        return visitorDao.getIdVisitors()
    }
}