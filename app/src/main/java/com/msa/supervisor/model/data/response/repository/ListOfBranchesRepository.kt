package com.msa.supervisor.model.data.response.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.dao.BranchesDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireHeaderDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireLineDao
import com.msa.supervisor.model.data.database.dao.UserLoginDao
import com.msa.supervisor.model.data.database.entity.BranchesEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHeaderEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
import com.msa.supervisor.model.data.database.entity.UserLoginEntity
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
class ListOfBranchesRepository @Inject constructor(
    private val apiSupervisor: ApiSupervisor,
    private val userLoginDao: UserLoginDao,
    private val branchesDao: BranchesDao,



    ) {



    suspend fun requestBranches() = apiCall { apiSupervisor.getOwnerKeys() }




    fun insertBranches(branchesEntity: List<BranchesEntity?>){
        branchesDao.deleteBranches()
        branchesDao.insertBranches(branchesEntity as List<BranchesEntity>)
    }


    fun getBranches():List<BranchesEntity>{
        return branchesDao.getBranches()
    }
    fun getBranchesId(ids: SupportSQLiteQuery):List<BranchesEntity>{
        return branchesDao.getBranchesQuery(ids)
    }

    fun getUserLogin(): UserLoginEntity {
        return userLoginDao.getUserLogin()
    }

}