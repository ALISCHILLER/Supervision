package com.msa.supervisor.model.data.database.entity

import java.util.Date
import java.util.UUID

data class CustomerPinEntity(
    var UniqueId: UUID? = null,

    var CustomerUniqueId: UUID? = null,

    var CustomerCode: String? = null,

    var CustomerName: String? = null,

    var PinDate: Date? = null,

    var PinPDate: String? = null,

    var DealerName: String? = null,

    var Pin1: String? = null,

    var Pin2: String? = null,

    var Pin3: String? = null,

    var Pin4: String? = null
) {
}