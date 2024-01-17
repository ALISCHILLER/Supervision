package com.msa.supervisor.model.data.request

import com.google.gson.annotations.SerializedName
/**
 * create by Ali Soleymani.
 */
data class OrderStatusRequestModel(

    @SerializedName("DealersId")
     var dealersId: List<String?>?,

    @SerializedName("StartDate")
     val startdata: String?,

    @SerializedName("EndDate")
     val endDate: String?,
) {
}