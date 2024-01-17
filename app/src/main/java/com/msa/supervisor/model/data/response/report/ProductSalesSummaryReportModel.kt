package com.msa.supervisor.model.data.response.report


import java.math.BigDecimal
/**
 * create by Ali Soleymani.
 */

data class ProductSalesSummaryReportModel(

    var productBackOfficeCode: String?,

    var productName: String?,


    var productCategoryCode: String?,


    var productCategoryName: String?,


    var productNetWeight: BigDecimal,


    var productNetCount_CA: BigDecimal,

    )
