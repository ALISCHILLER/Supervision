package com.msa.supervisor.model.data.response


/**
 * create by Ali Soleymani.
 */

data class GeneralResponse<T>(
    val hasError : Boolean,
    val message : String,
    val data: T?
)