package com.atrfan.yunpanapp.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.atrfan.yunpanapp.R
import com.atrfan.yunpanapp.databinding.FragmentLoginBinding
import com.atrfan.yunpanapp.network.request.LoginRequest
import com.google.android.material.snackbar.Snackbar
import com.kongzue.dialogx.dialogs.WaitDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        binding.passwordInputText.transformationMethod = PasswordTransformationMethod.getInstance()

        binding.loginButton.setOnClickListener {
            if(TextUtils.isEmpty(binding.usernameInputText.text) || TextUtils.isEmpty(binding.passwordInputText.text)){
                Snackbar
                    .make(binding.root, R.string.not_null, Snackbar.LENGTH_SHORT)
                    .show()
            }
             else {
                 // TODO 登录相关操作
                WaitDialog.show(R.string.logining)
                CoroutineScope(Dispatchers.Main).launch {
                    val loginEntity = withContext(Dispatchers.IO) {
                        LoginRequest.login(
                            binding.usernameInputText.text.toString(),
                            binding.passwordInputText.text.toString()
                        )
                    }
                    WaitDialog.dismiss()
                    if (loginEntity.id == -1) {
                        Toast.makeText(
                            this@LoginFragment.context,
                            "账号或密码错误",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
//                    Toast.makeText(this@LoginFragment.context,"登录成功",Toast.LENGTH_SHORT).show()
                        // TODO 跳转到MainActivity
                        val bundle = Bundle()
                        bundle.putString("username", loginEntity.username)
                        bundle.putString("password", loginEntity.password)
                        bundle.putInt("id", loginEntity.id)
                        bundle.putString("countSize", loginEntity.countSize)
                        bundle.putString("totalSize", loginEntity.totalSize)
                        findNavController().navigate(
                            R.id.action_loginFragment_to_mainActivity,
                            bundle
                        )
                    }
                }
            }
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.autoLoginChip.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                Log.d("LoginFragment","Cheched")
            } else {
                Log.d("LoginFragment","NotCheched")
            }

        }
        return binding.root
    }

}