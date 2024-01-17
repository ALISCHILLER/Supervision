package com.msa.supervisor.model.data.response.map

import android.os.Parcel
import android.os.Parcelable
/**
 * create by Ali Soleymani.
 */
data class TripPointModel(
    val dealerId:String,
    var lat : Float,
    var long : Float,
    var name : String,
    var time : String,
)  {
//
//    constructor(parcel: Parcel) : this(
//        parcel.readFloat(),
//        parcel.readFloat()
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeFloat(lat)
//        parcel.writeFloat(long)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<TripPointModel> {
//        override fun createFromParcel(parcel: Parcel): TripPointModel {
//            return TripPointModel(parcel)
//        }
//
//        override fun newArray(size: Int): Array<TripPointModel?> {
//            return arrayOfNulls(size)
//        }
//    }
}