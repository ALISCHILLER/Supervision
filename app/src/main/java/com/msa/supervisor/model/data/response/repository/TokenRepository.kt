package com.msa.supervisor.model.data.response.repository

import android.content.SharedPreferences
import com.msa.supervisor.tool.CompanionValues
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */

class TokenRepository @Inject constructor(private val sp: SharedPreferences ) {

    //---------------------------------------------------------------------------------------------- getBearerToken
    fun getBearerToken() = "Bearer ${sp.getString(CompanionValues.TOKEN, null)}"
    //---------------------------------------------------------------------------------------------- getBearerToken

    //---------------------------------------------------------------------------------------------- getToken
    fun getToken() = sp.getString(CompanionValues.tourrecive, null)
    //---------------------------------------------------------------------------------------------- getToken


    //---------------------------------------------------------------------------------------------- deleteToken
    fun deleteToken() {
        sp.edit()
            .putString(CompanionValues.TOKEN, null)
            .apply()
    }
    //---------------------------------------------------------------------------------------------- deleteToken
}