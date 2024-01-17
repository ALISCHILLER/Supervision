package com.msa.supervisor.model.data.response.map

import java.time.LocalDate
import java.util.*
/**
 * create by Ali Soleymani.
 */
data class GpsTrackingJsonModel(

    val dealerId: String?,
    val dealerIds:MutableList<String>?,
    val supervisorId: String,
    val json_str:String,
    val createdDate: String,
    var createdDate_str: String?,
)
