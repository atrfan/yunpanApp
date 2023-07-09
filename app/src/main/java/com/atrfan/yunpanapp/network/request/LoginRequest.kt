package com.atrfan.yunpanapp.network.request

import android.util.Log
import com.atrfan.yunpanapp.entity.LoginEntity
import com.atrfan.yunpanapp.network.LOGIN_TAG
import com.atrfan.yunpanapp.network.api.LoginApi
import com.atrfan.yunpanapp.network.client.HttpFactory

object LoginRequest {
    private val client = HttpFactory.client
    suspend fun login(username: String, password: String): LoginEntity {
//        Log.d(TAG,"in the method")
        val service = client.create(LoginApi::class.java)
        val resp = service.login(username, password).execute().body()
//        Log.d(TAG,resp.toString())
        resp?.data?.let {
            Log.d(LOGIN_TAG,it.toString())
            return LoginEntity(it.countSize, it.id, it.username, it.password, it.totalSize)
        }
       return LoginEntity("defalut",-1,"fadas","fadfasfd","fafsda")
    }
}