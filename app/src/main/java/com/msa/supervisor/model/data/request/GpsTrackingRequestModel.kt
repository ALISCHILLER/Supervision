package com.msa.supervisor.model.data.request

import java.util.Date
/**
 * create by Ali Soleymani.
 */
data class GpsTrackingRequestModel (
    var dealerId: String?,
    val dealerIds:MutableList<String>?,
    val supervisorId: String,
    val jsonStr: String,
    var createdDate: Date,
    var createdDate_str: String?,
    )