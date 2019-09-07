package com.dingdonginc.zhangfang.services

import android.content.Context
import android.widget.Toast

class ToastService(private val context: Context, val activityService: ActivityService) {
    fun makeText(text: String, during: Int) =
        Toast.makeText(activityService.activity, text, during)
}