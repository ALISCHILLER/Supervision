package com.msa.supervisor.model.data.response.customer

import java.util.Date
import java.util.UUID
/**
 * create by Ali Soleymani.
 */
data class CustomerPinModel(
    var uniqueId: UUID? ,
    var customerUniqueId: UUID?,

    var customerCode: String? ,

    var customerName: String? ,

    var pinDate: String? ,

    var pinPDate: String?,

    var dealerName: String?,

    var pin1: String?,

    var pin2: String?,

    var pin3: String?,

    var pin4: String?
) {
}