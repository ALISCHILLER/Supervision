package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.msa.supervisor.model.data.database.entity.BranchesEntity
import com.msa.supervisor.model.data.database.entity.CustomerapprovalEntity

/**
 * create by Ali Soleymani.
 */
@Dao
interface BranchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBranches(branchesEntity: List<BranchesEntity>)


    @Query("DELETE FROM branches")
    fun deleteBranches()

    @Query("Select * From branches ")
    fun getBranches():List<BranchesEntity>

    @Query("Select * From branches WHERE value IN (:ids) ")
    fun getBranchesId(ids:List<String>):List<BranchesEntity>

    @RawQuery
    fun getBranchesQuery(ids: SupportSQLiteQuery): List<BranchesEntity>
}