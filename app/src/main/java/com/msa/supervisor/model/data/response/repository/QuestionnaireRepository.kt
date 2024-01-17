package com.msa.supervisor.model.data.response.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.dao.ProductGroupDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireHeaderDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireHistoryDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireLineDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireLineOptionDao
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHeaderEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHistoryEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineOptionEntity
import com.msa.supervisor.model.data.request.SyncQuestionnaire
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
class QuestionnaireRepository @Inject constructor(
    private val apiSupervisor: ApiSupervisor,
    private val questionnaireHistoryDao: QuestionnaireHistoryDao,
    private val questionnaireHeaderDao: QuestionnaireHeaderDao,
    private val questionnaireLineDao: QuestionnaireLineDao,
    private val questionnaireLineOption: QuestionnaireLineOptionDao,
) {


    suspend fun sendQuestionnaire(syncGetTourModel: SyncQuestionnaire) =
        apiCall { apiSupervisor.sendQuestionnaire(syncGetTourModel) }

    fun getQuestionnaireHeader():List<QuestionnaireHeaderEntity>{
       return questionnaireHeaderDao.getQuestionnaireHeader()
    }
    fun getQuestionnaireHistoryValid(customerId:String
                                     ,questionnaireId: String):Boolean{
        return questionnaireHistoryDao.getQuestionnaireHistoryCustomerValid(customerId,questionnaireId)
    }

    fun insertQuestionnaireHistoryValid(questionnaireHistoryEntity: List<QuestionnaireHistoryEntity>){
        return questionnaireHistoryDao.insertQuestionnaireHistory(questionnaireHistoryEntity)
    }
    fun getActivity(questionnaireId: String,activityId:String):Boolean{
        return questionnaireHeaderDao.getActivityQuery(questionnaireId,activityId)
    }

    fun getCategory(questionnaireId: String,activityId:String):Boolean{
        return questionnaireHeaderDao.getCategoryQuery(questionnaireId,activityId)
    }

    fun getLevel(questionnaireId: String,activityId:String):Boolean{
        return questionnaireHeaderDao.getLevelQuery(questionnaireId,activityId)
    }



    fun getQuestionnaireLines(questionnaireId: String):List<QuestionnaireLineEntity>{
        return questionnaireLineDao.getQuestionnaireLines(questionnaireId)
    }


    fun getQuestionnaireLineOption(questionnaireId: String):List<QuestionnaireLineOptionEntity>{
        return questionnaireLineOption.getQuestionnaireLineOption(questionnaireId)
    }


}