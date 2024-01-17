package com.msa.supervisor.view.fragment.confirmorders

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.msa.supervisor.model.data.request.ConfirmOrderRequest
import com.msa.supervisor.model.data.request.DataConfrimOrderReguest
import com.msa.supervisor.model.data.response.order.OrderConfirmModel
import com.msa.supervisor.model.data.response.repository.OrderRepository
import com.msa.supervisor.model.data.response.repository.VisitorRepository
import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */
@HiltViewModel
class ConfirmOrdersViewModel @Inject constructor(
    private val visitorRepository: VisitorRepository,
    private val orderRepository: OrderRepository
) : BaseViewModel() {
    val visitorItems: MutableLiveData<List<MultiItem>>
            by lazy { MutableLiveData<List<MultiItem>>() }

    val confirmOrder: MutableLiveData<List<OrderConfirmModel>>
            by lazy { MutableLiveData<List<OrderConfirmModel>>() }

    val confirmOrderSend: MutableLiveData<Boolean>
            by lazy { MutableLiveData<Boolean>() }

    fun requestOrderConfirm(dealersId: List<String>) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
            val date: String = current.format(formatter)

            val confirmOrderRequest = ConfirmOrderRequest(dealersId, null, date, date)
            val response = callApi2(orderRepository.requestOrderConfirm(confirmOrderRequest))

            response?.let {
                Log.e(TAG, "requestOrderConfirm:${it}")
                confirmOrder.postValue(it)
            }
        }
    }


    fun requestSendDataOrderConfirm(
        dataOrderConfirm: DataConfrimOrderReguest
    ) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val response = callApi2(orderRepository.requestSendDataOrderConfirm(dataOrderConfirm))
            response?.let {
                Log.e(TAG, "requestSendDataOrderConfirm:${it}")
                confirmOrderSend.postValue(true)
            }
        }
    }

    fun getVisitor() {
        viewModelScope.launch(IO + exceptionHandler()) {
            val visitorEntity = visitorRepository.getVisitors()
            val visitorItemsList = visitorEntity.map { markersVisitor ->
                MultiItem(
                    markersVisitor.personnelName.toString(),
                    markersVisitor.personnelUniqueId,
                    ""
                )
            }

            visitorItems.postValue(visitorItemsList)
        }
    }
}