package com.msa.supervisor.model.data.response.order

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
/**
 * create by Ali Soleymani.
 */
data class ItemOrderModel(
    var productCategory: String?,

    var amount: Long,


    var productCode: String?,


    var productName: String?,


    var productCount: Int,


    var productCountStr: String?,


    var tax: Int,

    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productCategory)
        parcel.writeLong(amount)
        parcel.writeString(productCode)
        parcel.writeString(productName)
        parcel.writeInt(productCount)
        parcel.writeString(productCountStr)
        parcel.writeInt(tax)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemOrderModel> {
        override fun createFromParcel(parcel: Parcel): ItemOrderModel {
            return ItemOrderModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemOrderModel?> {
            return arrayOfNulls(size)
        }
    }
}