package com.atrfan.yunpanapp

import android.app.Application
import com.kongzue.dialogx.DialogX

class YunpanApplicatioin: Application() {
    override fun onCreate() {
        super.onCreate()
        DialogX.init(this)
        DialogX.onlyOnePopTip = true
    }
}