package com.atrfan.yunpanapp.network.pojo

import com.google.gson.annotations.SerializedName

data class LoginResp(
    @SerializedName("ret")
    val ret: Int,
    @SerializedName("msg")
    val meg: String,
    @SerializedName("data")
    val data: User
) {
    inner class User(
        @SerializedName("countSize")
        val countSize: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("username")
        val username: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("totalSize")
        val totalSize: String,
        @SerializedName("NAMESPACE")
        val NAMESPACE: String,
        @SerializedName("RECYCLE")
        val RECYCLE: String
    ){
        override fun toString(): String {
            return "User(countSize='$countSize', id=$id, username='$username', password='$password', totalSize='$totalSize', NAMESPACE='$NAMESPACE', RECYCLE='$RECYCLE')"
        }
    }
}