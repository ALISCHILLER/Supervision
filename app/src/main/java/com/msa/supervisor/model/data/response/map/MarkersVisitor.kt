package com.msa.supervisor.model.data.response.map

import org.osmdroid.views.overlay.Marker

data class MarkersVisitor(
    var visitorId:String?,
    var nameVisitor:String?,
    var marker: Marker?,
    val oldTracking:Long?,
    var time:String?
)
