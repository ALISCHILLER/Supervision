package com.msa.supervisor.model.data.response.repository

import com.msa.supervisor.model.api.ApiSupervisor
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */

class UserRepository @Inject constructor(
    private val apiSupervisor: ApiSupervisor,
    private val tokenRepository: TokenRepository
) {


    //---------------------------------------------------------------------------------------------- isEntered
    fun isEntered() = tokenRepository.getToken()?.let {
        true
    } ?: false
    //---------------------------------------------------------------------------------------------- isEntered


    //---------------------------------------------------------------------------------------------- getBearerToken
    fun getBearerToken() = tokenRepository.getBearerToken()
    //---------------------------------------------------------------------------------------------- getBearerToken


}