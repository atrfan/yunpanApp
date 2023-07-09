package com.atrfan.yunpanapp.network.api

import com.atrfan.yunpanapp.network.URL_LOGIN
import com.atrfan.yunpanapp.network.pojo.LoginResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApi {
    @GET(URL_LOGIN)
    fun login(@Query("username") username:String,@Query("password") password:String): Call<LoginResp>

}