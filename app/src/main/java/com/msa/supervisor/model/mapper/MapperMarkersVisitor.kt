package com.msa.supervisor.model.mapper

import com.msa.supervisor.model.data.database.entity.VisitorsEntity
import com.msa.supervisor.model.data.response.map.MarkersVisitor
/**
 * create by Ali Soleymani.
 */
class MapperMarkersVisitor :BaseMapper<List<VisitorsEntity>,List<MarkersVisitor>>{
    override fun transformToDomain(type: List<VisitorsEntity>): List<MarkersVisitor> {
        return  type.map { visitorModel ->
            MarkersVisitor(
                visitorId = visitorModel.personnelUniqueId.toString(),
                nameVisitor = visitorModel.personnelName.toString(),
                marker =null,
                oldTracking = 0,
                time = ""
            )
        }
    }

    override fun transformToDto(type: List<MarkersVisitor>): List<VisitorsEntity> {
        TODO("Not yet implemented")
    }


}