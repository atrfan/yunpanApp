package com.atrfan.yunpanapp.network

import com.atrfan.yunpanapp.network.request.LoginRequest
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LoginTest {
    @Test
    fun loginTest(){
        val username = "admin"
        val password = "1234"
        val res = runBlocking {
            LoginRequest.login(username, password)
        }
        println(res)
    }
}