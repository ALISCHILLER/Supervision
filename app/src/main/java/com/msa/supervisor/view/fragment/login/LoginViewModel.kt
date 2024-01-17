package com.msa.supervisor.view.fragment.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.msa.supervisor.R
import com.msa.supervisor.model.data.database.entity.UserLoginEntity
import com.msa.supervisor.model.data.request.LoginRequestModel
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireHeaderModel
import com.msa.supervisor.model.data.response.repository.LoginRepository
import com.msa.supervisor.model.data.response.repository.TourRepository
import com.msa.supervisor.model.data.response.tour.TourIdModel
import com.msa.supervisor.model.data.response.userlogin.UserToken
import com.msa.supervisor.model.enum.EnumRequestTour
import com.msa.supervisor.model.mapper.MapperQuestionnair
import com.msa.supervisor.tool.CompanionValues
import com.msa.supervisor.tool.datetime.DateFormat
import com.msa.supervisor.tool.datetime.DateHelper
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Created by m-latifi on 11/9/2022.
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var repository: LoginRepository,
    private var tourRepository: TourRepository
) : BaseViewModel() {
    private lateinit var enumRequestTour: EnumRequestTour

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val loginLiveDate: com.msa.supervisor.tool.SingleLiveEvent<Int> by lazy { com.msa.supervisor.tool.SingleLiveEvent<Int>() }
    val userNameError: com.msa.supervisor.tool.SingleLiveEvent<String> by lazy { com.msa.supervisor.tool.SingleLiveEvent<String>() }
    val passwordError: com.msa.supervisor.tool.SingleLiveEvent<String> by lazy { com.msa.supervisor.tool.SingleLiveEvent<String>() }
    val userName: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val password: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val requestTroue: MutableLiveData<Boolean?> by lazy { MutableLiveData<Boolean?>() }
    val requestTroueEnd: com.msa.supervisor.tool.SingleLiveEvent<Boolean?> by lazy { com.msa.supervisor.tool.SingleLiveEvent<Boolean?>() }
    var job: Job? = null

    //---------------------------------------------------------------------------------------------- login
    fun login(fromFingerPrint: Boolean, androidId: String, systemType: String) {
        if (fromFingerPrint)
            setUserNamePasswordFromSharePreferences()
        var valueIsEmpty = false
        if (userName.value.isNullOrEmpty()) {
            userNameError.postValue(resourcesProvider.getString(R.string.userNameIsEmpty))
            valueIsEmpty = true
        }
        if (password.value.isNullOrEmpty()) {
            passwordError.postValue(resourcesProvider.getString(R.string.passcodeIsEmpty))
            valueIsEmpty = true
        }
        if (!valueIsEmpty)
            requestLogin(androidId, systemType)
    }
    //---------------------------------------------------------------------------------------------- login


    //---------------------------------------------------------------------------------------------- requestLogin
    private fun requestLogin(androidId: String, systemType: String) {
        viewModelScope.launch(IO + exceptionHandler()) {
            if (userName.value.isNullOrEmpty() || password.value.isNullOrEmpty())
                setMessage(
                    resourcesProvider.getString(
                        R.string.dataSendingIsEmpty
                    )
                )
            else {
                val request = LoginRequestModel(
                    userName.value!!,
                    password.value!!,
                    systemType,
                    androidId
                )
                val tokenFirebase = sharedPreferences.getString(CompanionValues.tokenFirebase, "")
                val response = callApi2(
                    repository.requestUserToken(
                        userName.value, password.value, "", androidId,
                        tokenFirebase, ""
                    )
                )

                response?.let {
                    Log.e("Login", "requestLogin:${it} ")
                    saveUserNameAndPassword(it, null, null)
                    requestLogin()
                }

            }
        }
    }

    private fun requestLogin() {
        viewModelScope.launch(IO + exceptionHandler()) {
            if (userName.value.isNullOrEmpty() || password.value.isNullOrEmpty())
                setMessage(
                    resourcesProvider.getString(
                        R.string.dataSendingIsEmpty
                    )
                )
            else {
                val response = callApi2(
                    repository.requestLogin(
                        userName.value, password.value, "", ""
                    )
                )

                response?.let {
                    Log.e("Login", "requestLogin:${it} ")
                    repository.insertUserLogin(it)
                    saveUserNameAndPassword(null, it, null)
                }
            }

        }
    }

    //---------------------------------------------------------------------------------------------- requestLogin


    //---------------------------------------------------------------------------------------------- isBiometricEnable
    fun isBiometricEnable() = sharedPreferences.getBoolean(CompanionValues.biometric, false)
    //---------------------------------------------------------------------------------------------- isBiometricEnable


    //---------------------------------------------------------------------------------------------- setUserNamePasswordFromSharePreferences
    private fun setUserNamePasswordFromSharePreferences() {
        userName.value = sharedPreferences.getString(CompanionValues.userName, "")
        password.value = sharedPreferences.getString(CompanionValues.password, "")
    }
    //---------------------------------------------------------------------------------------------- setUserNamePasswordFromSharePreferences


    //---------------------------------------------------------------------------------------------- saveUserNameAndPassword
    private fun saveUserNameAndPassword(
        userToken: UserToken?,
        userLoginEntity: UserLoginEntity?, tourIdModel: TourIdModel?
    ) {
        if (userToken != null) {
            sharedPreferences
                .edit()
                .putString(CompanionValues.TOKEN, userToken.accessToken)
                .putString(CompanionValues.userName, userName.value)
                .putString(CompanionValues.password, password.value)
                .apply()
        }
        if (userLoginEntity != null) {
            val fields = userLoginEntity.zoneIds?.split(",")
            sharedPreferences
                .edit()
                .putString(CompanionValues.personnelName, userLoginEntity.personnelName)
                .putString(CompanionValues.userCode, userLoginEntity.personnelCode)
                .putString(CompanionValues.zoneIds, fields?.get(0))
                .putString(CompanionValues.userId, userLoginEntity.id)
                .apply()
            val type: Int
            if (fields?.size!! > 1)
                type = 1
            else
                type = 2


            sharedPreferences.edit()
                .putInt(CompanionValues.typeUser, type).apply()
            loginLiveDate.postValue(type)
        }
        if (tourIdModel != null) {
            sharedPreferences
                .edit()
                .putString(CompanionValues.tokenSignalR, tourIdModel.zarNotificationToken)
                .putString(CompanionValues.tourId, tourIdModel.uniqueId.toString())
                .apply()
        }

    }
    //---------------------------------------------------------------------------------------------- saveUserNameAndPassword


    //---------------------------------------------------------------------------------------------- saveNewIp
    fun saveNewIp(ip: String?) {
        sharedPreferences
            .edit()
            .putString(CompanionValues.URL, ip)
            .apply()
    }
    //---------------------------------------------------------------------------------------------- saveNewIp

    //---------------------------------------------------------------------------------------------- tour

    fun requestVisitor(type: Int) {
        val ids: String
        if (type == 2)
            ids = sharedPreferences.getString(CompanionValues.userId, "").toString()
        else
            ids = sharedPreferences.getString(CompanionValues.zoneIds, "").toString()

        job = CoroutineScope(IO + exceptionHandler()).launch {
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
                    requestCustomerapproval()
            }
        }
    }

    fun requestCustomerapproval() {
        val userId = sharedPreferences.getString(CompanionValues.userId, "")
        job = CoroutineScope(IO + exceptionHandler()).launch {
            val response = callApi2(tourRepository.requestCustomerapproval(userId.toString()))
            response?.let {
                Log.e("requestCustomer", "${it} ")
                tourRepository.insertCustomerApproval(response)
                requestTroue.postValue(true)
                requestQuestionnaireHeaders()
            }
        }
    }


    fun requestQuestionnaireHeaders() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler()).launch {
            val date = Date()
            val persianLocale = Locale("fa", "IR")
            val dateString = DateHelper.toString(date, DateFormat.Simple, persianLocale)
            val response = callApi2(
                tourRepository.requestQuestionnaireHeaders(
                    dateString,
                    "1e67dfff-e23d-461f-83ea-ba0974a46c1d"
                )
            )
            response?.let {
                Log.e("requestQuestionnaireHeaders", "${it} ")
                val zoneIds = sharedPreferences.getString(CompanionValues.zoneIds, "").toString().lowercase()
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

        job = CoroutineScope(Dispatchers.IO + exceptionHandler()).launch {
            val date = Date()
            var dateString = DateHelper.toString(date, DateFormat.MicrosoftDateTime, Locale.US)
            val response = callApi2(
                tourRepository.requestQuestionnaireHistory(
                    dateString
                )
            )
            response?.let {
                Log.e("QuestionnaireHistory", "${it} ")
                tourRepository.insertQuestionnaireHistory(it)
                requestTroue.postValue(true)
            }
            requestTourBySupervisorId()
        }
    }


    fun requestTourBySupervisorId() {
        val userId = sharedPreferences.getString(CompanionValues.userId, "")
        job = CoroutineScope(IO + exceptionHandler()).launch {
            val response = callApi2(tourRepository.requestTourId(userId.toString()))
            response?.let {
                Log.e("requestTourBySupervisorId", "${it} ")
                saveUserNameAndPassword(null, null, it)
                requestTroue.postValue(true)
                requestTour_sent()
            }
        }
    }


    fun requestTour_sent() {
        job = CoroutineScope(IO + exceptionHandler()).launch {
            val userId = sharedPreferences.getString(CompanionValues.tourId, "")
            val response = callApi3(tourRepository.requestTourreceived(userId))
            response.let {
                Log.e("requestTour_sent", "${it} ")
                sharedPreferences
                    .edit()
                    .putString(CompanionValues.tourrecive, "true")
                    .apply()
                requestTroueEnd.postValue(true)
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
}