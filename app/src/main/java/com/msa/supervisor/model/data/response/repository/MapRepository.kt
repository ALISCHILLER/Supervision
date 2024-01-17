package com.msa.supervisor.model.data.response.repository

import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.request.GpsTrackingRequestModel
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
class MapRepository@Inject constructor(
    private val apiSupervisor: ApiSupervisor
) {
    suspend fun requestMap(gpsTrackingJsonModel: GpsTrackingRequestModel)=
        apiCall { apiSupervisor.getGpsTrackingJson(gpsTrackingJsonModel) }

}