package com.atrfan.yunpanapp.network.request

import android.util.Log
import com.atrfan.yunpanapp.entity.RegisterEntity
import com.atrfan.yunpanapp.network.LOGIN_TAG
import com.atrfan.yunpanapp.network.api.RegisterApi
import com.atrfan.yunpanapp.network.client.HttpFactory

object RegisterRequest {
   private val client = HttpFactory.client
    suspend fun register(username: String, password: String): RegisterEntity {
//        Log.d(TAG,"in the method")
        val service = client.create(RegisterApi::class.java)
        val resp = service.register(username, password).execute().body()
//        Log.d(TAG,resp.toString())
        resp?.data?.let {
//            Log.d(LOGIN_TAG,it.toString())
            return RegisterEntity(it.countSize, it.id, it.username, it.password, it.totalSize)
        }
        return RegisterEntity("defalut",-1,"fadas","fadfasfd","fafsda")
    }
}