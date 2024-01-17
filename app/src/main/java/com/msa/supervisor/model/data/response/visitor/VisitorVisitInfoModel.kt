package com.msa.supervisor.model.data.response.visitor

import java.math.BigDecimal
import java.util.Currency

data class VisitorVisitInfoModel (
    var customerCount: Int,
    var totalAmount: BigDecimal?,
    var returnTotalAmount: BigDecimal?,
    var visitedCustomerCount: Int,
    var ordered: Int,
    var noOrder: Int,
    var noVisit: Int,
    var visited: Int,
    var returnCount: Int,
    )
{
}