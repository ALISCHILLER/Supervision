package com.msa.supervisor.model.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "questionnaireHeader")
data class QuestionnaireHeaderEntity (
    @PrimaryKey var uniqueId: String,
    var title: String?,
    var demandTypeUniqueId: String? ,
    var fromDate: String?,
    var fromPDate: String? ,
    var toDate: String? ,
    var toPDate: String?,
    var centerUniqueIds: String? ,
    var saleZoneUniqueIds: String?,
    var stateUniqueIds: String?,
    var cityUniqueIds: String?,
    var customerActivityUniqueIds: String?,
    var customerCategoryUniqueIds: String?,
    var customerLevelUniqueIds: String?,
    var saleOfficeUniqueIds: String?,
)
