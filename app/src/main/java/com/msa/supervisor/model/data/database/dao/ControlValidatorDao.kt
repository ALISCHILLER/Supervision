package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msa.supervisor.model.data.database.entity.ControlValidatorEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHeaderEntity


@Dao
interface ControlValidatorDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertControlValidator(controlValidatorEnitiy: List<ControlValidatorEntity>)

    @Query("DELETE FROM controlValidator")
    fun deleteControlValidator()

    @Query("SELECT * FROM controlValidator")
    fun getControlValidator():List<ControlValidatorEntity>

}