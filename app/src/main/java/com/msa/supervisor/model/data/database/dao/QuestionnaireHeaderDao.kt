package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.msa.supervisor.model.data.database.entity.BranchesEntity
import com.msa.supervisor.model.data.database.entity.ProductGroupEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHeaderEntity


@Dao
interface QuestionnaireHeaderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestionnaireHeader(questionnaireHeaderDao: List<QuestionnaireHeaderEntity>)

    @Query("DELETE FROM questionnaireHeader")
    fun deleteQuestionnaireHeader()
    @Query("SELECT * FROM questionnaireHeader")
    fun getQuestionnaireHeader():List<QuestionnaireHeaderEntity>



    @Query("SELECT EXISTS(SELECT * FROM " +
            "questionnaireHeader where uniqueId=:questionnairId and customerActivityUniqueIds LIKE '%' || :it || '%')")
    fun getActivityQuery(questionnairId:String,it:String):Boolean



    @Query("SELECT EXISTS(SELECT * FROM " +
            "questionnaireHeader where uniqueId=:questionnairId and customerCategoryUniqueIds LIKE '%' || :it || '%')")
    fun getCategoryQuery(questionnairId:String,it:String):Boolean

    @Query("SELECT EXISTS(SELECT * FROM " +
            "questionnaireHeader where uniqueId=:questionnairId and customerLevelUniqueIds LIKE '%' || :it || '%')")
    fun getLevelQuery(questionnairId:String,it:String):Boolean
}