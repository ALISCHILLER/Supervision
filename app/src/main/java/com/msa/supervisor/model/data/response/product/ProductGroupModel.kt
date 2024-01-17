package com.msa.supervisor.model.data.response.product

import java.util.*
/**
 * create by Ali Soleymani.
 */
data class ProductGroupModel(
    val productGroupId:UUID,
    val productGroupName:String?,
    val orderOf:Int,
    val lastUpdate: Date,
    val rowIndex:Int,
)
