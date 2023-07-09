package com.atrfan.yunpanapp.network.client

import com.atrfan.yunpanapp.network.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpFactory {
    @JvmStatic
    private var _client = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val client: Retrofit
        get() = _client
}