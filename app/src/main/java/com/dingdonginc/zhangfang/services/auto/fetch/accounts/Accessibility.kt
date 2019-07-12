package com.dingdonginc.zhangfang.services.auto.fetch.accounts

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class Accessibility : AccessibilityService() {
    override fun onCreate() {
        super.onCreate()
        Log.i("Accessibility", "on Create")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val root = rootInActiveWindow
        val pingzhengText = root.findAccessibilityNodeInfosByText("微信支付凭证")
        if (pingzhengText.size == 0)
            return
        for (every in pingzhengText) {
            val block = every.parent ?: continue
            for (i in 0 until block.childCount) {
                val child = block.getChild(i)
                child?: continue
                Log.i("Children", child.text?.toString()?: "null")
            }
        }
    }

    override fun onInterrupt() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}