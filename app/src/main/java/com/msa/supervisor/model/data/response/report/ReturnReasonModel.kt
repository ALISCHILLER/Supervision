package com.msa.supervisor.model.data.response.report

import com.google.gson.annotations.SerializedName
/**
 * create by Ali Soleymani.
 */
data class ReturnReasonModel(
    @SerializedName("reason")
    var reason: String?,

    @SerializedName("reasonCode")
    val reasonCode: String?,

    @SerializedName("productCountCa")
    val productCountCa: Double,

    )
