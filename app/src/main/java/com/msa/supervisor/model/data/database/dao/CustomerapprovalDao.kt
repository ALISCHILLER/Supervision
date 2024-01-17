package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.model.data.database.entity.CustomerapprovalEntity

@Dao
interface CustomerapprovalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomerapproval(customerapprovalEntity: List<CustomerapprovalEntity>)


    @Query("Select * From customerapproval")
    fun getCustomerapproval():List<CustomerapprovalEntity>

    @RawQuery(observedEntities = [CustomerapprovalEntity::class])
    fun searchAllFields(query: SupportSQLiteQuery): List<CustomerapprovalEntity>

}