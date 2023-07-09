package com.atrfan.yunpanapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.atrfan.yunpanapp.databinding.ActivityMainBinding
import com.atrfan.yunpanapp.entity.UserEntity

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    companion object{
        var user:UserEntity? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 关闭上一个activity
        user = ViewModelProvider(this)[UserEntity::class.java]
        LoginActivity.loginActivity?.finish()
        binding = ActivityMainBinding.inflate(layoutInflater)
        user?.let {
            it.username = intent.getStringExtra("username").toString()
            it.password = intent.getStringExtra("password").toString()
            it.id = intent.getIntExtra("id",-1)
            it.countSize = intent.getStringExtra("countSize").toString()
            it.totalSize = intent.getStringExtra("totalSize").toString()
        }
        setContentView(binding.root)


    }
}