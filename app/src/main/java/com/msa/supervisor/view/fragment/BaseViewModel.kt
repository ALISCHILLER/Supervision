package com.msa.supervisor.view.fragment

import androidx.lifecycle.ViewModel
import com.zar.core.enums.EnumApiError
import com.zar.core.models.ErrorApiModel
import com.zar.core.tools.api.checkResponseError
import com.msa.supervisor.R
import com.msa.supervisor.di.ResourcesProvider
import com.msa.supervisor.model.data.response.GeneralResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by m-latifi on 10/8/2022.
 */

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider
    val errorLiveDate: com.msa.supervisor.tool.SingleLiveEvent<ErrorApiModel> by lazy { com.msa.supervisor.tool.SingleLiveEvent<ErrorApiModel>() }



    suspend fun <T : Any> callApi3(
        response: Response<T?>?
    ): T? {
        if (response?.isSuccessful == true) {
            val body = response.body()
            body.let { generalResponse ->
                return generalResponse
            }
        } else setMessage(response)
        return null
    }

    //---------------------------------------------------------------------------------------------- callApi
    suspend fun <T : Any> callApi2(
        response: Response<T?>?
    ): T? {
        if (response?.isSuccessful == true) {
            val body = response.body()
            body?.let { generalResponse ->
                return generalResponse
            } ?: run {
                setMessage(
                    resourcesProvider.getString(
                        R.string.dataReceivedIsEmpty
                    )
                )
            }
        } else setMessage(response)
        return null
    }
    //---------------------------------------------------------------------------------------------- callApi



    //---------------------------------------------------------------------------------------------- callApi
    suspend fun <T : Any> callApi(
        response: Response<GeneralResponse<T?>>?,
        showMessageAfterSuccessResponse: Boolean = false
    ): T? {
        if (response?.isSuccessful == true) {
            val body = response.body()
            body?.let { generalResponse ->
                if (generalResponse.hasError)
                    setMessage(generalResponse.message)
                else {
                    if (generalResponse.data == null)
                        setMessage(
                            resourcesProvider.getString(
                                R.string.dataReceivedIsEmpty
                            )
                        )
                    else {
                        if (showMessageAfterSuccessResponse)
                            setMessage(generalResponse.message)
                        return generalResponse.data
                    }
                }
            } ?: run {
                setMessage(
                    resourcesProvider.getString(
                        R.string.dataReceivedIsEmpty
                    )
                )
            }
        } else setMessage(response)
        return null
    }
    //---------------------------------------------------------------------------------------------- callApi


    //---------------------------------------------------------------------------------------------- setError
    suspend fun setMessage(response: Response<*>?) {
        withContext(Main) {
            checkResponseError(response, errorLiveDate)
        }
    }
    //---------------------------------------------------------------------------------------------- setError


    //---------------------------------------------------------------------------------------------- setMessage
    fun setMessage(message: String) {
        errorLiveDate.postValue(ErrorApiModel(EnumApiError.Error, message))
    }
    //---------------------------------------------------------------------------------------------- setMessage


    //---------------------------------------------------------------------------------------------- exceptionHandler
    fun exceptionHandler() = CoroutineExceptionHandler { _, throwable ->
        CoroutineScope(Main).launch {
            throwable.localizedMessage?.let { setMessage(it) }
        }
    }
    //---------------------------------------------------------------------------------------------- exceptionHandler

}