package com.msa.supervisor.model.data.response.report

import com.google.gson.annotations.SerializedName
/**
 * create by Ali Soleymani.
 */
data class OrderStatusReportModel(
    @SerializedName("date")
     var date: String?,
    @SerializedName("orderWeight")
     val orderWeight: Double,

    @SerializedName("pendingOrderWeight")
     val pendingOrderWeight: Double,

    @SerializedName("inProgressOrderWeight")
     val inProgressOrderWeight: Double,

    @SerializedName("undeliverdOrderWeight")
     val undeliverdOrderWeight: Double,

    @SerializedName("finalWeight")
     val finalWeight: Double,

     val dealersItems: List<DealersItemModel>?

    )
