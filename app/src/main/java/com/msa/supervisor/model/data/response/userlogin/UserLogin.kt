package com.msa.supervisor.model.data.response.userlogin

import java.util.UUID
/**
 * create by Ali Soleymani.
 */
data class UserLogin(

    val zoneIds: String?,
    val deviceId: String?,
    val token: String?,
    val personnelcode: String?,
    val fullNamePersonel: String,
    val applicationOwnerId: String?,
    val dataOwnerID: String?,
    val personnelID: String?,
    val dataOwnerCenterID: String?,
    val ownerKey: String?,
    val dataOwnerKey: String?,
    val dataOwnerCenterKey: String?
) {
}