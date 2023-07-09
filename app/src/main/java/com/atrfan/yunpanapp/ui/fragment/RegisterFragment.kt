package com.atrfan.yunpanapp.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.atrfan.yunpanapp.R
import com.atrfan.yunpanapp.databinding.FragmentRegisterBinding
import com.atrfan.yunpanapp.network.request.RegisterRequest
import com.google.android.material.snackbar.Snackbar
import com.kongzue.dialogx.dialogs.WaitDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.registerPasswordInputText.transformationMethod =
            PasswordTransformationMethod.getInstance()
        binding.passwordInputTextAgain.transformationMethod =
            PasswordTransformationMethod.getInstance()

        // 注册相关监听事件
        binding.register.setOnClickListener {
            // 检查再次输入的密码与初始输入的密码是否相同
            if (TextUtils.isEmpty(binding.registerUsernameInputText.text)) {
                binding.registerUsernameInputLayout.editText?.isFocusable = true
                binding.registerUsernameInputLayout.editText?.isFocusableInTouchMode = true
                binding.registerUsernameInputLayout.editText?.requestFocus()
                Snackbar
                    .make(binding.root, R.string.username_is_null, Snackbar.LENGTH_SHORT)
                    .show()
            } else if (TextUtils.isEmpty(binding.registerPasswordInputText.text)) {
                binding.registerPasswordInputLayout.editText?.isFocusable = true
                binding.registerPasswordInputLayout.editText?.isFocusableInTouchMode = true
                binding.registerPasswordInputLayout.editText?.requestFocus()
                Snackbar
                    .make(binding.root, R.string.password_is_null, Snackbar.LENGTH_SHORT)
                    .show()
            } else if (TextUtils.isEmpty(binding.passwordInputTextAgain.text)) {
                binding.passwordInputLayoutAgain.editText?.isFocusable = true
                binding.passwordInputLayoutAgain.editText?.isFocusableInTouchMode = true
                binding.passwordInputLayoutAgain.editText?.requestFocus()
                Snackbar
                    .make(binding.root, R.string.repassword_is_null, Snackbar.LENGTH_SHORT)
                    .show()
            } else if (binding.passwordInputTextAgain.text.toString() != binding.registerPasswordInputText.text.toString()
            ) {
                binding.passwordInputLayoutAgain.editText?.isFocusable = true
                binding.passwordInputLayoutAgain.editText?.isFocusableInTouchMode = true
                binding.passwordInputLayoutAgain.editText?.requestFocus()
                Snackbar
                    .make(binding.root, R.string.password_not_same, Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                // TODO 注册相关请求
                WaitDialog.show(R.string.registering)
                CoroutineScope(Dispatchers.Main).launch {
                    val registerEntity = withContext(Dispatchers.IO) {
                        RegisterRequest.register(
                            binding.registerUsernameInputText.text.toString(),
                            binding.registerPasswordInputText.text.toString()
                        )
                    }
                    WaitDialog.dismiss()
                    if (registerEntity.id == -1) {
                        Toast.makeText(
                            this@RegisterFragment.context,
                            "注册失败",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@RegisterFragment.context,
                            "注册成功",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.goToLogin.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

}