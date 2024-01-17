package com.msa.supervisor.model.data.response.repository

import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.dao.ControlValidatorDao
import com.msa.supervisor.model.data.database.dao.CustomerDao
import com.msa.supervisor.model.data.database.dao.CustomerapprovalDao
import com.msa.supervisor.model.data.database.dao.ProductGroupDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireHeaderDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireHistoryDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireLineDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireLineOptionDao
import com.msa.supervisor.model.data.database.dao.VisitorsDao
import com.msa.supervisor.model.data.database.entity.ControlValidatorEntity
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.model.data.database.entity.CustomerapprovalEntity
import com.msa.supervisor.model.data.database.entity.ProductGroupEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHeaderEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHistoryEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineOptionEntity
import com.msa.supervisor.model.data.database.entity.VisitorsEntity
import retrofit2.http.Query
import java.util.UUID
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
class TourRepository @Inject constructor(
    private val apiSupervisor: ApiSupervisor,
    private val customerDao: CustomerDao,
    private val visitorDao: VisitorsDao,
    private val productGroupDao: ProductGroupDao,
    private val customerapprovalDao: CustomerapprovalDao,
    private val questionnaireHistoryDao: QuestionnaireHistoryDao,
    private val questionnaireHeaderDao: QuestionnaireHeaderDao,
    private val questionnaireLineDao: QuestionnaireLineDao,
    private  val controlValidatorEntity: ControlValidatorDao,
    private  val questionnaireLineOptionDao: QuestionnaireLineOptionDao
) {


    suspend fun requestVisitors(ids: String, type: String) =
        apiCall { apiSupervisor.getVisitors(ids, type) }

    suspend fun requestCustomerAll(dealersId: List<String>) =
        apiCall { apiSupervisor.getsupervisorCustomers(dealersId) }

    suspend fun requestProductGroup(deteAfter: String) =
        apiCall { apiSupervisor.getProductGroup(deteAfter) }

    suspend fun requestQuestionnaireHeaders(
        date: String,
        subSystemType: String
    ) = apiCall { apiSupervisor.getQuestionnaireHeaders(date, subSystemType) }

    suspend fun requestQuestionnaireHistory(date: String) =
        apiCall { apiSupervisor.getQuestionnaireHistory(date) }

    suspend fun requestCustomerapproval(supervisorId: String) =
        apiCall { apiSupervisor.getCustomerapproval(supervisorId) }


    suspend fun requestTourId(userId: String) =
        apiCall { apiSupervisor.getTourId(userId) }

    suspend fun requestCustomerPinCodes() =
        apiCall { apiSupervisor.getPinCodes() }


    suspend fun requestTourreceived(userId: String?) =
        apiCall { apiSupervisor.tourSend(userId) }


    suspend fun requestLogout(userId: String)= apiCall { apiSupervisor.tourreceived(userId) }


    suspend fun requestNews()= apiCall { apiSupervisor.requestNews() }

    fun insertvisitor(VisitorEntity: List<VisitorsEntity>) {
        visitorDao.deleteVisitors()
        visitorDao.insert(VisitorEntity)
    }

    fun insertCustomerAll(customer: List<CustomerEntity>) {
        customerDao.deleteCustomer()
        customerDao.insertCustomer(customer)
    }


    fun insertProductGroup(productGroupEntity: List<ProductGroupEntity>) {
        productGroupDao.insertProductGroup(productGroupEntity)
    }

    fun insertCustomerApproval(customerapprovalEntity: List<CustomerapprovalEntity>) {
        customerapprovalDao.insertCustomerapproval(customerapprovalEntity)
    }
    fun insertQuestionnaireHistory(QuestionnaireHistoryEntity: List<QuestionnaireHistoryEntity>) {
        questionnaireHistoryDao.deleteQuestionnaireHistory()
        questionnaireHistoryDao.insertQuestionnaireHistory(QuestionnaireHistoryEntity)
    }

    fun insertQuestionnaireHeader(questionnaireHeaderEntity: List<QuestionnaireHeaderEntity>){
        questionnaireHeaderDao.insertQuestionnaireHeader(questionnaireHeaderEntity)
    }
    fun deleteQuestionnaireHeader(){
        questionnaireHeaderDao.deleteQuestionnaireHeader()
    }

    fun insertQuestionnaireLine(questionnaireLineEntity: List<QuestionnaireLineEntity>){
        questionnaireLineDao.insertQuestionnaireLine(questionnaireLineEntity)
    }



    fun insertquestionnaireValidator(controlValidatorEnitiy: List<ControlValidatorEntity>){
        controlValidatorEntity.insertControlValidator(controlValidatorEnitiy)
    }

    fun deleteControlValidator(){
        controlValidatorEntity.deleteControlValidator()
    }

    fun insertQuestionnaireLineOption(questionnaireLineOptionEntity: List<QuestionnaireLineOptionEntity>){
        questionnaireLineOptionDao.insertQuestionnaireLineOption(questionnaireLineOptionEntity)
    }
    fun deleteQuestionnaireLineOption(){
        questionnaireLineOptionDao.deleteQuestionnaireLineOption()
    }

    fun deleteQuestionnaireLines(){
        questionnaireLineDao.deleteQuestionnaireLines()
    }

    fun getCustomerAll(): List<CustomerEntity> {
        return customerDao.getCustomer()
    }

    fun getDealersId(): List<String> {
        return visitorDao.getIdVisitors()
    }

}