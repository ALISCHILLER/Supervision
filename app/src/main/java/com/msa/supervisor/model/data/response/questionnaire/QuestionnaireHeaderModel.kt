package com.msa.supervisor.model.data.response.questionnaire

import com.google.gson.annotations.SerializedName
import java.util.Date
import java.util.UUID
/**
 * create by Ali Soleymani.
 */
data class QuestionnaireHeaderModel(
    var uniqueId:String,
    var title: String?,
    var demandTypeUniqueId: UUID?,
    var fromDate: String? ,
    var fromPDate: String? ,
    var toDate: String? ,
    var toPDate: String? ,
    var centerUniqueIds: String? ,
    @SerializedName("saleAreaUniqueIds") //we receive AreaId as ZoneId from Webservice
    var saleZoneUniqueIds: String?,
    var stateUniqueIds: String? ,
    var cityUniqueIds: String? ,
    var customerActivityUniqueIds: String?,
    var customerCategoryUniqueIds: String?,
    var customerLevelUniqueIds: String? ,
    var saleOfficeUniqueIds: String? ,
    var questionnaireLines: List<QuestionnaireLineModel>?
) {
}