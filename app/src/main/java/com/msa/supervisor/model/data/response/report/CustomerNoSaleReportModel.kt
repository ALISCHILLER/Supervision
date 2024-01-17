package com.msa.supervisor.model.data.response.report

import java.util.UUID
/**
 * create by Ali Soleymani.
 */
data class CustomerNoSaleReportModel(

    var backOfficeId: Int,

    var customerName: String?,

    var customerCode: String?,

    var address: String?,

    var phone: String?,

    var storeName: String?,

    var mobile: String?,

    var nationalCode: String?,

    var cityId: UUID?,

    var customerActivity: String?,

    var customerCategory: String?,
)
