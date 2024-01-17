package com.msa.supervisor.model.data.response.visitor

import java.util.*
/**
 * create by Ali Soleymani.
 */
data class VisitorModel(
    var uniqueId: UUID,
    val name: String?,
    val status: Int,
    val phone: String?,
)
