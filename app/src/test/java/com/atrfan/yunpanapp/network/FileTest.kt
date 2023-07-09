package com.atrfan.yunpanapp.network

import com.atrfan.yunpanapp.network.request.FileRequest
import org.junit.Test

class FileTest {
    @Test
    fun getFileListTest(){
        val path = "/"
        val username = "admin"
        val res = FileRequest.getFileList(path,username)
        println(res)
    }
}