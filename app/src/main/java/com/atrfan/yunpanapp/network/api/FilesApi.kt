package com.atrfan.yunpanapp.network.api

import com.atrfan.yunpanapp.network.URL_DOWNLOAD
import com.atrfan.yunpanapp.network.URL_FILESLIST
import com.atrfan.yunpanapp.network.URL_UPLOAD
import com.atrfan.yunpanapp.network.pojo.FileListResp
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface FilesApi {
    // 测试完毕后要将ResponseBody改为FileListResp
    @GET(URL_FILESLIST)
    fun getFileList(
        @Query("path") path: String = "/",
        @Query("username") username: String
    ): Call<FileListResp>

    @GET(URL_DOWNLOAD)
    fun downloadFile(
        @Query("currentPath") currentPath: String,
        @Query("downPath") downPath: String,
        @Query("username") username: String
    ): Call<ResponseBody>

    @POST("/file/upload")
    fun uploadFile(
        @Body body: MultipartBody
    ): Call<ResponseBody>
}