package com.msa.supervisor.model.mapper

import com.msa.supervisor.model.data.database.entity.ControlValidatorEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHeaderEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineOptionEntity
import com.msa.supervisor.model.data.response.questionnaire.ControlValidatorModel
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireHeaderModel
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireLineModel
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireLineOptionModel
import java.util.UUID
/**
 * create by Ali Soleymani.
 */
object MapperQuestionnair {

    fun questionnaireHeaderModelToEntity(model: QuestionnaireHeaderModel): QuestionnaireHeaderEntity {
        return QuestionnaireHeaderEntity(
            uniqueId = model.uniqueId,
            title = model.title,
            demandTypeUniqueId = model.demandTypeUniqueId?.toString(),
            fromDate = model.fromDate,
            fromPDate = model.fromPDate,
            toDate = model.toDate,
            toPDate = model.toPDate,
            centerUniqueIds = model.centerUniqueIds,
            saleZoneUniqueIds = model.saleZoneUniqueIds,
            stateUniqueIds = model.stateUniqueIds,
            cityUniqueIds = model.cityUniqueIds,
            customerActivityUniqueIds = model.customerActivityUniqueIds,
            customerCategoryUniqueIds = model.customerCategoryUniqueIds,
            customerLevelUniqueIds = model.customerLevelUniqueIds,
            saleOfficeUniqueIds = model.saleOfficeUniqueIds
        )
    }


    fun QuestionnaireLineModelmapToEntity(model: QuestionnaireLineModel): QuestionnaireLineEntity {
        return QuestionnaireLineEntity(
            uniqueId = model.uniqueId,
            questionnaireUniqueId = model.questionnaireUniqueId?.toString(),
            title = model.title,
            questionnaireLineTypeUniqueId = model.questionnaireLineTypeUniqueId?.toString(),
            hasAttachment = model.hasAttachment,
            numberOfOptions = model.numberOfOptions,
            attachmentTypeUniqueId = model.attachmentTypeUniqueId?.toString(),
            questionGroupUniqueId = model.questionGroupUniqueId?.toString(),
            isMandatory=model.isMandatory,
            isRemoved=model.isRemoved,
            rowIndex = model.rowIndex,
            minValue = model.minValue,
            maxValue = model.maxValue
        )
    }

    fun ControlValidatorModelmapToEntity(model: ControlValidatorModel): ControlValidatorEntity {
        return ControlValidatorEntity(
            uniqueId = model.uniqueId,
            name = model.name,
            message = model.message,
            min = model.min,
            max = model.max
        )
    }


    fun QuestionnaireLineOptionModelmapToEntity(model: QuestionnaireLineOptionModel): QuestionnaireLineOptionEntity {
        return QuestionnaireLineOptionEntity(
            uniqueId = model.uniqueId,
            questionnaireLineUniqueId = model.questionnaireLineUniqueId,
            title = model.title,
            rowIndex = model.rowIndex,
            isRemoved=model.isRemoved,
            questionGroupUniqueId = model.questionGroupUniqueId,
            answerAttachmentUniqueId = model.answerAttachmentUniqueId
        )
    }

    fun questionnaireLineEntitiesMapToModels(entities: List<QuestionnaireLineEntity>): List<QuestionnaireLineModel> {
        val models = mutableListOf<QuestionnaireLineModel>()
        for (entity in entities) {
            val model = QuestionnaireLineModel(
                uniqueId = entity.uniqueId,
                questionnaireUniqueId = entity.questionnaireUniqueId?.toString(),
                title = entity.title,
                questionnaireLineTypeUniqueId = entity.questionnaireLineTypeUniqueId?.toString(),
                hasAttachment = entity.hasAttachment,
                numberOfOptions = entity.numberOfOptions,
                attachmentTypeUniqueId = entity.attachmentTypeUniqueId?.toString(),
                questionGroupUniqueId = entity.questionGroupUniqueId?.toString(),
                isMandatory = entity.isMandatory,
                isRemoved = entity.isRemoved,
                rowIndex = entity.rowIndex,
                questionnaireLineOptions = null,
                validators = null,
                maxValue = entity.maxValue,
                minValue = entity.minValue
            )
            models.add(model)
        }
        return models
    }
}