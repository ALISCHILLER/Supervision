package com.msa.supervisor.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msa.supervisor.model.data.database.entity.UserLoginEntity


@Dao
interface UserLoginDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserLogin(userLoginEntity: UserLoginEntity)

    @Query("DELETE FROM userLogin")
    fun deleteUserLogin()
    @Query("SELECT * FROM userLogin")
    fun getUserLogin():UserLoginEntity
}