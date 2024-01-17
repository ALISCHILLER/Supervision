package com.msa.supervisor.model.data.response.report

import com.google.gson.annotations.SerializedName
/**
 * create by Ali Soleymani.
 */
data class ReturnDealerModel(
    @SerializedName("dealerName")
     var dealerName: String?,
    @SerializedName("dealerCode")
     val dealerCode: String?,

    @SerializedName("productCountCa")
     val productCountCa: Double,

    @SerializedName("dealersItems")
     val customerModels: List<ReturnCustomerModel>?
)
