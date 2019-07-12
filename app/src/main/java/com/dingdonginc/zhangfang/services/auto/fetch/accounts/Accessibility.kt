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
        Log.i("ClassName", event.className.toString())
        var root = rootInActiveWindow
        var pingzhengText = root.findAccessibilityNodeInfosByText("微信支付凭证")
        for (every in pingzhengText) {
            var block = every.parent
            for (i in 0..block.childCount) {
                var child = block.getChild(i)
                Log.i(child.className.toString(), child.text.toString())
            }
            break
        }
        Log.i("Accessibility", pingzhengText.toString())
    }

    override fun onInterrupt() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}