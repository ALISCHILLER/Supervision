package com.msa.supervisor.model.data.response.report

import com.msa.supervisor.tool.Currency
import java.math.BigDecimal
/**
 * create by Ali Soleymani.
 */
data class ProductInvoiveBalanceReportModel(
    val customerBackOfficeCode: String,
    val customerName: String,
    val invoiceNumber: String,
    val invoiceShmsiDate: String,
    val invoiceOverDue: String,
    val invoiceFinalPrice: BigDecimal,
    val paidPose: BigDecimal,
    val paidCash: BigDecimal,
    val paidCheck: BigDecimal,
    val ivoiceRemain: BigDecimal,
    val usancePaid: BigDecimal,
)
