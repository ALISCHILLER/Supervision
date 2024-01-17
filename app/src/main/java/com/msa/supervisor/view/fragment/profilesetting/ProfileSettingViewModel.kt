package com.msa.supervisor.view.fragment.profilesetting

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.msa.supervisor.model.data.database.entity.UserLoginEntity
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireHeaderModel
import com.msa.supervisor.model.data.response.repository.ListOfBranchesRepository
import com.msa.supervisor.model.data.response.repository.TourRepository
import com.msa.supervisor.model.mapper.MapperQuestionnair
import com.msa.supervisor.tool.CompanionValues
import com.msa.supervisor.tool.datetime.DateFormat
import com.msa.supervisor.tool.datetime.DateHelper
import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */
@HiltViewModel
class ProfileSettingViewModel @Inject constructor(
    private val listOfBranchesRepository: ListOfBranchesRepository,
    private var tourRepository: TourRepository
) : BaseViewModel() {
    val listBranchItems: MutableLiveData<List<MultiItem>>
            by lazy { MutableLiveData<List<MultiItem>>() }

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val logout: MutableLiveData<Boolean?> by lazy { MutableLiveData<Boolean?>() }
    val requestTroue: MutableLiveData<Boolean?> by lazy { MutableLiveData<Boolean?>() }
    val requestTroueEnd: com.msa.supervisor.tool.SingleLiveEvent<Boolean?> by lazy { com.msa.supervisor.tool.SingleLiveEvent<Boolean?>() }
    val user: com.msa.supervisor.tool.SingleLiveEvent<UserLoginEntity?> by lazy { com.msa.supervisor.tool.SingleLiveEvent<UserLoginEntity?>() }
    private var job: Job? = null

    fun getListOfBranches() {

        viewModelScope.launch(IO + exceptionHandler()) {

            val userLogin = listOfBranchesRepository.getUserLogin()
            val fields = userLogin.zoneIds?.split(",")

            var queryCondition = "WHERE " + fields?.joinToString(separator = " or ") {
                "value LIKE '%$it%'"
            }
            val query = SimpleSQLiteQuery(
                "SELECT * FROM branches $queryCondition "
            )

            val listBranchEntity = query.let {
                listOfBranchesRepository.getBranchesId(it)
            }

            val listBranch = listBranchEntity.map { listBranchEntity ->
                MultiItem(listBranchEntity.title.toString(), listBranchEntity.value, "")
            }

            listBranchItems.postValue(listBranch)
        }
    }


    fun getUser() {
        viewModelScope.launch(IO + exceptionHandler()) {
            val userLogin = listOfBranchesRepository.getUserLogin()
            user.postValue(userLogin)
        }
    }

    fun requestVisitor() {
        job = CoroutineScope(IO + exceptionHandler()).launch {
            val type = sharedPreferences.getInt(CompanionValues.typeUser, 2)
            val ids: String
            if (type == 2)
                ids = sharedPreferences.getString(CompanionValues.userId, "").toString()
            else
                ids = sharedPreferences.getString(CompanionValues.zoneIds, "").toString()
            val response = callApi2(
                tourRepository.requestVisitors(
                    ids,
                    type.toString()
                )
            )
            response?.let {
                Log.e("requestVisitor", "${it} ")
                tourRepository.insertvisitor(response)
                requestTroue.postValue(true)
                requestCustomerAll(type)
            }
        }
    }


    fun requestCustomerAll(type: Int) {
        job = CoroutineScope(IO + exceptionHandler()).launch {
            val dealers = tourRepository.getDealersId()
            val response = callApi2(tourRepository.requestCustomerAll(dealersId = dealers))
            response?.let {
                Log.e("requestCustomerAll", "${it} ")
                tourRepository.insertCustomerAll(it)
                requestTroue.postValue(true)
                requestProductGroup(type)
            }
        }
    }


    fun requestProductGroup(type: Int) {
        job = CoroutineScope(IO + exceptionHandler()).launch {
            val response = callApi2(tourRepository.requestProductGroup("1987-04-01T00:00:00.00"))
            response?.let {
                Log.e("requestCustomer", "${it} ")
                tourRepository.insertProductGroup(it)
                requestTroue.postValue(true)
                if (type == 1) {
                    requestTroueEnd.postValue(true)
                    sharedPreferences
                        .edit()
                        .putString(CompanionValues.tourrecive, "true")
                        .apply()
                } else
                    requestQuestionnaireHeaders()

            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun requestQuestionnaireHeaders() {
        job = CoroutineScope(IO + exceptionHandler()).launch {
            val date = Date()
            val persianLocale = Locale("fa", "IR")
            var dateString = DateHelper.toString(date, DateFormat.Simple, persianLocale)
            val response = callApi2(
                tourRepository.requestQuestionnaireHeaders(
                    dateString,
                    "1e67dfff-e23d-461f-83ea-ba0974a46c1d"
                )
            )
            response?.let {
                Log.e("requestCustomer", "${it} ")
                val zoneIds = sharedPreferences.getString(CompanionValues.zoneIds, "").toString()
                tourRepository.deleteQuestionnaireHeader()
                tourRepository.deleteQuestionnaireLines()
                tourRepository.deleteControlValidator()
                tourRepository.deleteQuestionnaireLineOption()
                it.forEach {
                    val fields = it.centerUniqueIds?.split(",")
                    fields?.forEach { f ->
                        val temp = f.trimStart().trimEnd()
                        if (temp == zoneIds)
                            insetQuestionner(it)
                    } ?: run {
                        insetQuestionner(it)
                    }
                }
                requestTroue.postValue(true)
            }
            requestQuestionnaireHistory()
        }
    }

    fun requestQuestionnaireHistory() {

        job = CoroutineScope(IO + exceptionHandler()).launch {
            val date = Date()
            val dateString = DateHelper.toString(date, DateFormat.MicrosoftDateTime, Locale.US)
            val response = callApi2(
                tourRepository.requestQuestionnaireHistory(
                    dateString
                )
            )
            response?.let {
                Log.e("QuestionnaireHistory", "${it} ")
                tourRepository.insertQuestionnaireHistory(it)
                requestTroue.postValue(true)
                requestTroueEnd.postValue(true)
                sharedPreferences
                    .edit()
                    .putString(CompanionValues.tourrecive, "true")
                    .apply()
            }
        }
    }


    fun insetQuestionner(questionnaireHeaderModel: QuestionnaireHeaderModel) {
        val questionnaireHeaderEntity =
            MapperQuestionnair.questionnaireHeaderModelToEntity(questionnaireHeaderModel)
        tourRepository.insertQuestionnaireHeader(
            listOf(questionnaireHeaderEntity)
        )


        questionnaireHeaderModel.questionnaireLines?.forEach {
            val questionnaireLineEntity =
                MapperQuestionnair.QuestionnaireLineModelmapToEntity(it)
            tourRepository.insertQuestionnaireLine(
                listOf(
                    questionnaireLineEntity
                )
            )

            it.validators?.forEach {
                val questionnaireValidatorEntity =
                    MapperQuestionnair.ControlValidatorModelmapToEntity(it)

                tourRepository.insertquestionnaireValidator(listOf(questionnaireValidatorEntity))
            }

            it.questionnaireLineOptions?.forEach {
                val questionnaireLineOptionsEntity =
                    MapperQuestionnair.QuestionnaireLineOptionModelmapToEntity(it)

                tourRepository.insertQuestionnaireLineOption(listOf(questionnaireLineOptionsEntity))
            }

        }
    }


    fun requestLogout() {
        viewModelScope.launch(IO + exceptionHandler()) {
            val type = sharedPreferences.getInt(CompanionValues.typeUser, 2)
            val userId = sharedPreferences.getString(CompanionValues.tourId, "").toString()

            type.takeIf { it == 2 }?.let {
                val response = tourRepository.requestLogout(userId)
                response.let {
                    saveCheckTour()
                }
            } ?: run {
                saveCheckTour()
            }

        }
    }



    private fun saveCheckTour(){
        sharedPreferences
            .edit()
            .putString(CompanionValues.tourrecive, null)
            .apply()

        logout.postValue(true)
    }
}
