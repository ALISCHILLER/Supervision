package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msa.supervisor.model.data.database.entity.QuestionnaireHistoryEntity

@Dao
interface QuestionnaireHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestionnaireHistory(questionnaireHistoryEntity: List<QuestionnaireHistoryEntity>)

    @Query("SELECT * FROM questionnaireHistory")
    fun getQuestionnaireHistory():List<QuestionnaireHistoryEntity>


    @Query("DELETE FROM questionnaireHistory")
    fun deleteQuestionnaireHistory()


    @Query("SELECT * FROM questionnaireHistory WHERE customerId = :customer AND questionnaireId = :questionnaire")
    fun getQuestionnaireHistoryCustomer(customer:String,questionnaire: String)
    :List<QuestionnaireHistoryEntity>


    @Query("SELECT EXISTS(SELECT * FROM questionnaireHistory" +
            " WHERE customerId = :customer AND questionnaireId = :questionnaire)")
    fun getQuestionnaireHistoryCustomerValid(customer:String,questionnaire: String)
            :Boolean

}