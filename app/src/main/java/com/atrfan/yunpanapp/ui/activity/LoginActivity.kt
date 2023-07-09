package com.atrfan.yunpanapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.atrfan.yunpanapp.R
import com.atrfan.yunpanapp.databinding.ActivityLoginBinding
import com.google.android.material.chip.Chip

class LoginActivity : AppCompatActivity() {
    companion object{
        var loginActivity:LoginActivity? = null
    }

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this),null,false)
        setContentView(binding.root)
        loginActivity = this
    }
}