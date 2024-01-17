package com.msa.supervisor.model.data.response.tour

import java.util.UUID
/**
 * create by Ali Soleymani.
 */
data class TourIdModel(
    val uniqueId:UUID,
    val zarNotificationToken:String?
)