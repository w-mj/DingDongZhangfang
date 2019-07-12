package com.dingdonginc.zhangfang.services.auto.fetch.accounts

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class Accessibility : AccessibilityService() {
    override fun onCreate() {
        super.onCreate()
        Log.i("Accessibility", "on Create")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val root = rootInActiveWindow
        val pingzhengText = root.findAccessibilityNodeInfosByText("付款金额")
        if (pingzhengText.size == 0)
            return
        val billingItemList = pingzhengText[0].parent?.parent ?: return

        var lastTime: String? = null
        loop@ for (i in 0 until billingItemList.childCount) {
            val item = billingItemList.getChild(i)
            item?:continue
            when(item.className.toString()) {
                "android.widget.TextView" -> {
                    lastTime = item.text.toString()
                    Log.i("时间", lastTime)
                }
                "android.widget.LinearLayout" -> {
                    lastTime ?: continue@loop
                    if (item.childCount < 10) {
                        for (j in 0 until item.childCount) {
                            val text_in = item.getChild(j)
                            Log.e("Children < 10", text_in.text?.toString() ?: "null")
                        }
                    } else {
                        Log.i("金额", item.getChild(3).text?.toString()?:"null")
                        Log.i("对方", item.getChild(5).text?.toString()?:"null")
                        Log.i("状态", item.getChild(7).text?.toString()?:"null")

                    }
                }
                else -> {
                    Log.e("Unknown Class",item.className.toString() )
                }
            }

        }
    }

    override fun onInterrupt() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}