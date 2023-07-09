package com.atrfan.yunpanapp.network.api

import com.atrfan.yunpanapp.network.URL_REGISTER
import com.atrfan.yunpanapp.network.pojo.RegisterResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RegisterApi {
    @GET(URL_REGISTER)
    fun register(@Query("username") username:String, @Query("password") password:String):Call<RegisterResp>
}