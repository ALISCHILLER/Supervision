package com.msa.supervisor.model.data.response.report

import com.google.gson.annotations.SerializedName
/**
 * create by Ali Soleymani.
 */
data class ReturnCustomerModel(
    @SerializedName("customerName")
     var customerName: String?,

    @SerializedName("customerCode")
     val customerCode: String?,

    @SerializedName("productCountCa")
     val productCountCa: Double,

    @SerializedName("customerItems")
     val returnReasonModels: List<ReturnReasonModel>?

)
