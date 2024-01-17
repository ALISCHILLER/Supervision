package com.msa.supervisor.model.data.response.customer

import java.util.UUID
/**
 * create by Ali Soleymani.
 */
data class CustomerApprovalModel(
    val customerName: String?,
    val customerCode: String?,
    val Address: String?,
    val phone: String?,
    val storeName: String?,
    val mobile: String?,
    val nationalCode: String?,
    val customerLevel: String?,
    val customerActivity: String?,
    val customerCategory: String?,
    val dealerId: UUID,
    val dealerName: String?,
    val pathTitle: String?,
    val latitude: Double,
    val longitude: Double,
    val centerId: UUID?,
    val cityId: UUID?,
    val customerActivityId: UUID?,
    val customerCategoryId: UUID?,
    val customerLevelId: UUID?,
    val areaId: UUID?,
    val stateId: UUID?,
    val isActive: Boolean,
    val ispenddingChange: Boolean,
    val newStoreName: String?,
    val newAddress: String?,
    val newPhone: String?,
    val newMobile: String?,
    val newPostCode: String?,
    val newCustomerLevelName: String?,
    val newCustomerActivityName: String?,
    val newCustomerCategoryName: String?,
    val newNationalCode: String?,
    val newEconomicCode: String?,
    val newCityName: String?,
    val postCode: String?,
    val economicCode: String?,
    val codeNaghs: String?,
    val newCodeNaghdh: String?
    )
