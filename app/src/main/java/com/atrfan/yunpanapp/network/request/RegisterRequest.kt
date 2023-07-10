package com.atrfan.yunpanapp.network.request

import android.util.Log
import com.atrfan.yunpanapp.entity.RegisterEntity
import com.atrfan.yunpanapp.network.LOGIN_TAG
import com.atrfan.yunpanapp.network.api.RegisterApi
import com.atrfan.yunpanapp.network.client.HttpFactory

object RegisterRequest {
   private val client = HttpFactory.client
    suspend fun register(username: String, password: String): String {
        return try{
            val service = client.create(RegisterApi::class.java)
            Log.d(LOGIN_TAG, "register:username:$username,password:$password")
            val resp = service.register(username, password).execute().body()
            val str = resp?.string()
            Log.d("TESTMETHOD",resp?.string().toString())
            str ?: ""
        } catch (e:Exception){
            ""
        }
    }
}