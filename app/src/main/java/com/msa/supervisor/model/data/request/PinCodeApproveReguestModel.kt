package com.msa.supervisor.model.data.request

/**
 * create by Ali Soleymani.
 */

data class PinCodeApproveReguestModel(
    var CustomerId: String?,
    var CustomerCallOrderId: String?,
    var PinType: String? ,
    var DealerId: String? ,
    var DealerCode: String?,
){
}