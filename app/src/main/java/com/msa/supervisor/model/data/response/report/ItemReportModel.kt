package com.msa.supervisor.model.data.response.report

import com.msa.supervisor.model.enum.EnumRequestReport
/**
 * create by Ali Soleymani.
 */
data class ItemReportModel(
    val type:EnumRequestReport,
    val name:String
){
}