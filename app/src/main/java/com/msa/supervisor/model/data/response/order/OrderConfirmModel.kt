package com.msa.supervisor.model.data.response.order

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
/**
 * create by Ali Soleymani.
 */
data class OrderConfirmModel
    (
    var orderNumber: String?,

    var orderStatus: String?,


    var orderDate: String?,


    var dealerCode: String?,


    var dealerName: String?,


    var customerCode: String?,


    var customerName: String?,


    var paymentType: String?,


    var comment: String?,


    var customerCategory: String?,

    var items: List<ItemOrderModel>?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(ItemOrderModel)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(orderNumber)
        parcel.writeString(orderStatus)
        parcel.writeString(orderDate)
        parcel.writeString(dealerCode)
        parcel.writeString(dealerName)
        parcel.writeString(customerCode)
        parcel.writeString(customerName)
        parcel.writeString(paymentType)
        parcel.writeString(comment)
        parcel.writeString(customerCategory)
        parcel.writeTypedList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderConfirmModel> {
        override fun createFromParcel(parcel: Parcel): OrderConfirmModel {
            return OrderConfirmModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderConfirmModel?> {
            return arrayOfNulls(size)
        }
    }
}