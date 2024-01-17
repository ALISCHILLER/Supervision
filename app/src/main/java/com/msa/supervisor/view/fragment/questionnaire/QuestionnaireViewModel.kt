package com.msa.supervisor.view.fragment.questionnaire

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHeaderEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHistoryEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineOptionEntity
import com.msa.supervisor.model.data.request.SyncCustomerCallQuestionnaire
import com.msa.supervisor.model.data.request.SyncCustomerQuestionnaireAnswerModel
import com.msa.supervisor.model.data.request.SyncQuestionnaire
import com.msa.supervisor.model.data.request.SyncQuestionnaireCustomer
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireLineModel
import com.msa.supervisor.model.data.response.repository.CustomerRepository
import com.msa.supervisor.model.data.response.repository.QuestionnaireRepository
import com.msa.supervisor.model.mapper.MapperQuestionnair
import com.msa.supervisor.tool.CompanionValues
import com.msa.supervisor.tool.datetime.DateFormat
import com.msa.supervisor.tool.datetime.DateHelper
import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val questionnaireRepository: QuestionnaireRepository

) : BaseViewModel() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val customersQuestionnaire: com.msa.supervisor.tool.SingleLiveEvent<List<CustomerEntity>> by
    lazy { com.msa.supervisor.tool.SingleLiveEvent<List<CustomerEntity>>() }

    val questionnairesHeadr: com.msa.supervisor.tool.SingleLiveEvent<List<MultiItem>> by
    lazy { com.msa.supervisor.tool.SingleLiveEvent<List<MultiItem>>() }

    val questionnaireLine: MutableLiveData<List<QuestionnaireLineModel>> by
    lazy { MutableLiveData<List<QuestionnaireLineModel>>() }

    val questionnairesHistoryValid: com.msa.supervisor.tool.SingleLiveEvent<Boolean> by
    lazy { com.msa.supervisor.tool.SingleLiveEvent<Boolean>() }

    val questionnaireLineOption: com.msa.supervisor.tool.SingleLiveEvent<List<QuestionnaireLineOptionEntity>> by
    lazy { com.msa.supervisor.tool.SingleLiveEvent<List<QuestionnaireLineOptionEntity>>() }

    val sendQuestionnaire: com.msa.supervisor.tool.SingleLiveEvent<Boolean> by
    lazy { com.msa.supervisor.tool.SingleLiveEvent<Boolean>() }


    private val questionnaireLineOptions: MutableList<QuestionnaireLineOptionEntity> = ArrayList()


    fun getCustomer() {
        viewModelScope.launch(IO + exceptionHandler()) {
            val customers = customerRepository.getCustomerAll()
            customersQuestionnaire.postValue(customers)
        }
    }

    fun getSearchCustomer(text: String?) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val query = SimpleSQLiteQuery(
                "SELECT * FROM customer WHERE uniqueId LIKE '%$text%' " +
                        "OR customerCode LIKE '%$text%' " +
                        "OR customerName LIKE '%$text%' " +
                        "OR address LIKE '%$text%' " +
                        "OR phone LIKE '%$text%' " +
                        "OR storeName LIKE '%$text%' " +
                        "OR nationalCode LIKE '%$text%'" +
                        " OR mobile LIKE '%$text%' " +
                        "OR dealerName LIKE '%$text%'" +
                        " OR customerLevel LIKE '%$text%'" +
                        " OR customerCategory LIKE '%$text%'"
            )
            val results = customerRepository.searchAllFields(query)
            customersQuestionnaire.postValue(results)
        }
    }

    fun getQuestionnaireHistory(customerId: String, questionnaireId: String) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val questionnaireHistory = questionnaireRepository
                .getQuestionnaireHistoryValid(customerId, questionnaireId)
            questionnairesHistoryValid.postValue(questionnaireHistory)
        }
    }


    fun getQuestionnaireHeader(customer: CustomerEntity) {
        viewModelScope.launch(IO + exceptionHandler()) {

            val questionnaireHeader = questionnaireRepository
                .getQuestionnaireHeader()

            val questionnaireHeaderEntity: MutableList<QuestionnaireHeaderEntity> = ArrayList()


            questionnaireHeader.forEach {
                val customerActivityValdit: Boolean
                var customerCategorValdit: Boolean
                var customerLevelValdit = false


                if (it.customerActivityUniqueIds != null && it.customerActivityUniqueIds!!.isNotEmpty()) {
                    customerActivityValdit = questionnaireRepository.getActivity(
                        it.uniqueId,
                        customer.customerActivityId.toString()
                    )
                } else
                    customerActivityValdit = true

                if (it.customerCategoryUniqueIds != null && it.customerCategoryUniqueIds!!.isNotEmpty()) {
                    customerCategorValdit = questionnaireRepository.getCategory(
                        it.uniqueId,
                        customer.customerCategoryId.toString()
                    )
                } else
                    customerCategorValdit = true


                if (it.customerLevelUniqueIds != null && it.customerLevelUniqueIds!!.isNotEmpty()) {
                    customerCategorValdit = questionnaireRepository.getLevel(
                        it.uniqueId,
                        customer.customerCategoryId.toString()
                    )
                } else
                    customerLevelValdit = true

                if (customerLevelValdit && customerActivityValdit && customerCategorValdit)
                    questionnaireHeaderEntity.add(it)
            }

            val listquestionnaire = questionnaireHeaderEntity.map { questionnaireHeaderEntity ->
                MultiItem(
                    questionnaireHeaderEntity.title.toString(), questionnaireHeaderEntity.uniqueId,
                    customer.uniqueId.toString()
                )
            }
            questionnairesHeadr.postValue(listquestionnaire)
        }
    }

    fun getQuestionnaireLine(questionnaireId: String) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val questionnaireLinesEnity = questionnaireRepository
                .getQuestionnaireLines(questionnaireId)

            val questionnaireLines =
                MapperQuestionnair.questionnaireLineEntitiesMapToModels(questionnaireLinesEnity)

            questionnaireLine.postValue(questionnaireLines)

            questionnaireLines.forEach {
                val questionnaireLineOption = questionnaireRepository
                    .getQuestionnaireLineOption(it.uniqueId)

                questionnaireLineOptions.addAll(questionnaireLineOption)
            }
            questionnaireLineOption.postValue(questionnaireLineOptions)


        }
    }


    fun checkValidationAnswerByIndex(index: Int): Boolean {
        val item = questionnaireLine.value?.get(index)
        return if (item?.isMandatory == true)
            (item.selectedAnswerId == null &&
                    (item.selectedAnswersId == null || item.selectedAnswersId?.size == 0) &&
                    item.answer == null)
        else
            true
    }


    fun sendQuestionnaire(customerId: String, questionnaireId: String) {
        viewModelScope.launch(IO + exceptionHandler()) {
            questionnaireLine.let {
                val syncQuestionnaire = syncQuestionnaireMapper(it, customerId, questionnaireId)
                val response = callApi2(
                    questionnaireRepository.sendQuestionnaire(syncQuestionnaire)
                )
                response?.let {
                    Log.e(TAG, "sendQuestionnaire: $it")
                    questionnaireRepository.insertQuestionnaireHistoryValid(
                        listOf(
                            QuestionnaireHistoryEntity(
                                customerId + questionnaireId,
                                questionnaireId,
                                customerId
                            )
                        )
                    )
                    sendQuestionnaire.postValue(true)
                }
            }
        }
    }


    private fun syncQuestionnaireMapper(
        item: MutableLiveData<List<QuestionnaireLineModel>>,
        customerId: String,
        questionnaireId: String
    )
            : SyncQuestionnaire {
        val date = Date()
        val persianLocale = Locale("fa", "IR")
        val endData: String = DateHelper.toString(date, DateFormat.Date, persianLocale)

        val syncQuestionnaireAnswerModelList = mutableListOf<SyncCustomerQuestionnaireAnswerModel>()
        item.value?.forEach { answer ->
            val syncQuestionnaireAnswerModel = SyncCustomerQuestionnaireAnswerModel(
                UUID.randomUUID().toString(),
                answer.uniqueId,
                answer.selectedAnswerId,
                answer.answer,
                answer.selectedAnswerId,
                answer.hasAttachment
            )
            syncQuestionnaireAnswerModelList.add(syncQuestionnaireAnswerModel)
        }
        val syncCustomerCallQuestionnaire = SyncCustomerCallQuestionnaire(
            questionnaireId,
            syncQuestionnaireAnswerModelList
        )
        val customerCallQuestionnairesList = mutableListOf<SyncCustomerCallQuestionnaire>()
        customerCallQuestionnairesList.add(syncCustomerCallQuestionnaire)
        val syncQuestionnaireCustomer = SyncQuestionnaireCustomer(
            customerId,
            Date(),
            endData,
            Date(),
            endData,
            Date(),
            endData,
            51.48330420255661,
            51.48330420255661,
            Date(),
            endData,
            245000,
            customerCallQuestionnairesList
        )

        val tourId = sharedPreferences.getString(CompanionValues.tourId, "").toString()
        val customerCallsList = mutableListOf<SyncQuestionnaireCustomer>()
        customerCallsList.add(syncQuestionnaireCustomer)

        return SyncQuestionnaire(
            tourId,
            Date(),
            "3.0.1.29",
            customerCallsList
        )
    }


}