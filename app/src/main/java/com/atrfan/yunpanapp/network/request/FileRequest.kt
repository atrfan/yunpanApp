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
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.BufferedSink
import okio.BufferedSource
import okio.Okio
import retrofit2.Call
import retrofit2.http.Query
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
        val currentPathBody = RequestBody.create(MediaType.parse("multipart/form-data"),currentPath)
        val usernameBody = RequestBody.create(MediaType.parse("multipart/form-data"),username)
        val body = MultipartBody.Builder()
            .addPart(currentPathBody)
            .addPart(usernameBody)
            .addPart(MultipartBody.Part.create(MyRequestBody(contentResolver,uri)))
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