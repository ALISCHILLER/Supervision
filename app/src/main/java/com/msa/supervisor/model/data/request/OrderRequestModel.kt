package com.msa.supervisor.model.data.request
/**
 * create by Ali Soleymani.
 */
data class OrderRequestModel(
    val dealersId: List<String?>?,
    val statuses: List<String>?,
    val startDate: String?,
    val endDate: String?
) {

}