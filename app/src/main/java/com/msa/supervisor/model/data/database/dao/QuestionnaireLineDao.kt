package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msa.supervisor.model.data.database.entity.QuestionnaireHeaderEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHistoryEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
@Dao
interface QuestionnaireLineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestionnaireLine(questionnaireLineEntity: List<QuestionnaireLineEntity>)

    @Query("SELECT * FROM questionnaireLine WHERE questionnaireUniqueId = :questionnaireId")
    fun getQuestionnaireLines(questionnaireId: String)
            :List<QuestionnaireLineEntity>


    @Query("DELETE FROM questionnaireLine")
    fun deleteQuestionnaireLines()

}