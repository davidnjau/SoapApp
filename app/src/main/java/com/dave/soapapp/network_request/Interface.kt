package com.dave.soapapp.network_request

import com.dave.soapapp.helper.DbResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Interface {

    @Headers("Connection:close")
    @GET("QYLTRLogins")
    suspend fun verifyUser(
        @Query("Company") Company: String,
    ): Response<DbResult>
}