package com.msa.supervisor.model.data.response.report

import com.msa.supervisor.tool.Currency
import java.math.BigDecimal
/**
 * create by Ali Soleymani.
 */

data class CustomerSalesSummaryReportModel(

    var customerGroup: String?,

    var customerGroupTXT: String?,


    var customerActivity: String?,


    var customerActivityTXT: String?,


    var netWeight: BigDecimal,


    var netCount_CA: BigDecimal,
) {
}