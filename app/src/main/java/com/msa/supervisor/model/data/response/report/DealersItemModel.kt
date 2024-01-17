package com.msa.supervisor.model.data.response.report

import com.google.gson.annotations.SerializedName
/**
 * create by Ali Soleymani.
 */
data class DealersItemModel(
    @SerializedName("date")
     var date: String?,
    @SerializedName("dealerName")
     val dealerName: String?,

    @SerializedName("dealerCode")
     val dealerCode: String?,

    @SerializedName("orderWeight")
     val orderWeight: Double,

    @SerializedName("pendingOrderWeight")
     val pendingOrderWeight: Double,

    @SerializedName("inProgressOrderWeight")
     val inProgressOrderWeight: Double,

    @SerializedName("undeliverdOrderWeight")
     val undeliverdOrderWeight: Double,

    @SerializedName("finalWeight")
     var deliverdOrderWeight: Double,
    @SerializedName("customerItems")
    var customerItemModels: List<CustomerItemModel>?
)
