package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.msa.supervisor.model.data.database.entity.CustomerEntity


@Dao
interface CustomerDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customerEntity: List<CustomerEntity>)
    @Query("Select * From customer")
    fun getCustomer():List<CustomerEntity>

    @Query("DELETE FROM customer")
    fun deleteCustomer()

    @RawQuery(observedEntities = [CustomerEntity::class])
    fun searchAllFields(query: SupportSQLiteQuery): List<CustomerEntity>
}