package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msa.supervisor.model.data.database.entity.ControlValidatorEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineOptionEntity


@Dao
interface QuestionnaireLineOptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestionnaireLineOption(questionnaireLineOptionEnitiy: List<QuestionnaireLineOptionEntity>)
    @Query("DELETE FROM questionnaireLineOption")
    fun deleteQuestionnaireLineOption()

    @Query("SELECT * FROM questionnaireLineOption")
    fun getQuestionnaireLineOption():List<QuestionnaireLineOptionEntity>


    @Query("SELECT * FROM questionnaireLineOption where questionnaireLineUniqueId=:questionnaireLineId")
    fun getQuestionnaireLineOption(questionnaireLineId : String):List<QuestionnaireLineOptionEntity>

}