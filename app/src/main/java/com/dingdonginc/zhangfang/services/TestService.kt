package com.dingdonginc.zhangfang.services

import android.content.Context

class TestService(private val context: Context) {
    fun packageName(): String? {
        return context.packageName
    }
}