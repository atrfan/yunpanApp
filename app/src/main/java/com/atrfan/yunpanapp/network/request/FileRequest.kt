package com.atrfan.yunpanapp.network.request

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import com.atrfan.yunpanapp.network.FILE_TAG
import com.atrfan.yunpanapp.network.api.FilesApi
import com.atrfan.yunpanapp.network.client.HttpFactory
import com.atrfan.yunpanapp.network.pojo.Data
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okio.BufferedSink
import okio.BufferedSource
import okio.Okio
import retrofit2.Retrofit
import java.io.FileInputStream

object FileRequest {
    @JvmStatic
    private val client = HttpFactory.client
    @JvmStatic
    val service: FilesApi = client.create(FilesApi::class.java)
    suspend fun getFileList(path:String,username:String): List<Data>? {
        val resp = service.getFileList(path,username).execute().body()
        resp!!.data?.let{
            return it
        }
        return null
    }
    suspend fun downloadFile( currentPath:String, downPath:String, username:String): BufferedSource? {
        val resp = service.downloadFile(currentPath, downPath, username).execute().body()
        return resp?.source()
    }


    suspend fun uploadFile(currentPath:String,uri: Uri,contentResolver: ContentResolver,username:String): String? {
        val client = OkHttpClient.Builder().build()
        val service = Retrofit.Builder()
            .baseUrl("http://192.168.137.164:8086/")
            .client(client)
            .build().create(FilesApi::class.java)

        val body = MultipartBody.Builder()
            .addFormDataPart("username", username)
            .addFormDataPart("currentPath", currentPath)
            .addPart(MultipartBody.Part.createFormData("files", "image.png", MyRequestBody(contentResolver, uri)))
            .build()
        val resp = service.uploadFile(body).execute()
        Log.d("FileListFragment",resp.code().toString())
        return resp.body()?.string()
    }


    private class MyRequestBody(val contentResolver: ContentResolver, val uri: Uri) : RequestBody() {
        override fun contentType(): MediaType? {
            return MediaType.get("multipart/form-data")
        }

        override fun writeTo(sink: BufferedSink) {
            contentResolver.openFileDescriptor(uri, "r")?.let { fd ->
                Okio.source((FileInputStream(fd.fileDescriptor))).use {
                    sink.writeAll(it)
                }
            }
        }
    }

}