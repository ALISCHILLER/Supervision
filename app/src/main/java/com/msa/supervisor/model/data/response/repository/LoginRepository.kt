package com.msa.supervisor.model.data.response.repository

import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.dao.UserLoginDao
import com.msa.supervisor.model.data.database.entity.UserLoginEntity
import com.msa.supervisor.model.data.request.LoginRequestModel
import retrofit2.http.Field
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */

class LoginRepository @Inject constructor(
    private val apiSupervisor: ApiSupervisor,
    private val userLoginDao: UserLoginDao
    ){

    //---------------------------------------------------------------------------------------------- requestLogin

   suspend fun requestUserToken(username: String?,
                                 password: String?,
                                  scope: String?,
                                 deviceId: String?,
                                token: String?,
                                SystemTypeId: String?)=
       apiCall { apiSupervisor.requestUserToken(username,password,"password",
           "79a0d598-0bd2-45b1-baaa-0a9cf9eff240,62d0ac38-68bf-430a-8d38-f44fcd2159fb,62d0ac38-68bf-430a-8d38-f44fcd2159fb",
           deviceId, token, "", "1e67dfff-e23d-461f-83ea-ba0974a46c1d") }

    suspend fun requestLogin(username: String?,
                             password: String?,t: String,d:String) =
        apiCall{ apiSupervisor.requestLogin(username, password,
            t.isNotEmpty().toString(),d.isNotEmpty().toString()) }
    //---------------------------------------------------------------------------------------------- requestLogin

    fun insertUserLogin(userLoginEntity: UserLoginEntity){
        userLoginDao.deleteUserLogin()
        userLoginDao.insertUserLogin(userLoginEntity)
    }
}