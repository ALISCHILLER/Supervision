package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msa.supervisor.model.data.database.entity.ProductGroupEntity


@Dao
interface ProductGroupDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductGroup(productGroupEntity: List<ProductGroupEntity>)


    @Query("SELECT * FROM productGroup")
    fun getProductGroup():List<ProductGroupEntity>

    @Query("SELECT uniqueId FROM productGroup")
    fun getProductGroupId():List<String>


}