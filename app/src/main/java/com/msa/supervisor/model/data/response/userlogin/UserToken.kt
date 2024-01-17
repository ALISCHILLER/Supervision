package com.msa.supervisor.model.data.response.userlogin

import com.google.gson.annotations.SerializedName
/**
 * create by Ali Soleymani.
 */
data class UserToken(

    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
) {
}