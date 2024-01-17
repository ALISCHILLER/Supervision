package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msa.supervisor.model.data.database.entity.VisitorsEntity


@Dao
interface VisitorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(visitorsEntity: List<VisitorsEntity>)

    @Query("SELECT * FROM visitors")
    fun getVisitors():List<VisitorsEntity>

    @Query("SELECT personnelUniqueId FROM visitors")
    fun getIdVisitors():List<String>

    @Query("DELETE FROM visitors")
    fun deleteVisitors()

}