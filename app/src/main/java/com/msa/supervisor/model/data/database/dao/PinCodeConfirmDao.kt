package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.msa.supervisor.model.data.database.entity.PinCodeConfirmEntity


@Dao
interface PinCodeConfirmDao {

    @Insert
    fun insertPinCodeConfirm(pinCodeConfirmEntity: List<PinCodeConfirmEntity>)

    @Query("SELECT * FROM pinCodeConfirm")
    fun getPinCodeConfirm(): List<PinCodeConfirmEntity>

}