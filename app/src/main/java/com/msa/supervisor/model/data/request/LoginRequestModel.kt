package com.msa.supervisor.model.data.request

/**
 * create by Ali Soleymani.
 */

data class LoginRequestModel(
    val userName : String,
    val password : String,
    val SystemType: String,
    val AndroidId: String
)
