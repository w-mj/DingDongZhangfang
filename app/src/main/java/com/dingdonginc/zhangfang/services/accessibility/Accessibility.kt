package com.dingdonginc.zhangfang.services.accessibility

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.lang.StringBuilder
import java.util.*

class Accessibility : AccessibilityService() {
    override fun onCreate() {
        super.onCreate()
        Log.i("Accessibility", "on Create")
    }

    private var webList: Vector<AccessibilityNodeInfo> = Vector()
    private fun findWebView(root: AccessibilityNodeInfo) {
        for (i in 0 until root.childCount) {
            val child = root.getChild(i)
            if (child.className == "android.webkit.WebView") {
                webList.addElement(child)
            } else {
                findWebView(child)
            }
        }
    }

    private fun printChildren(root: AccessibilityNodeInfo, intent:Int = 0) {
        val builder = StringBuilder()
        for (i in 0 until intent) {
            builder.append('=')
        }
        Log.i(root.className.toString(), builder.toString() + (root.text?.toString()?:"null"))
        for (i in 0 until root.childCount) {
            printChildren(root.getChild(i), intent + 1)
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent): Unit {
        val root = rootInActiveWindow
        val pingzhengText = root.findAccessibilityNodeInfosByText("付款金额")
        if (pingzhengText.isNotEmpty()) {
            return ParseWechatList.parse(pingzhengText)
        }
        val zhangdanxiangqing = root.findAccessibilityNodeInfosByText("账单详情")
        if (zhangdanxiangqing.isNotEmpty()) {
            webList.clear()
            findWebView(root)
            for (i in 0 until webList.size) {
                ParseWechatDetail.parse(webList[i])
//                printChildren(webList[i])
            }
        }
    }

    override fun onInterrupt() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}