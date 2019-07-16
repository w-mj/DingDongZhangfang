package com.dingdonginc.zhangfang.services.auto.fetch.accounts
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo

object ParseWechatDetail {
    fun parse(item: AccessibilityNodeInfo) {
        if (item.className.toString() != "android.webkit.WebView") {
            Log.e(item.className.toString(), "class is not a webview")
            return
        }
        val detailRoot = item.getChild(0).getChild(0)
        Log.i("商家名", detailRoot.getChild(1).text.toString())
        Log.i("金额", detailRoot.getChild(2).text.toString())
        val moreDetail = detailRoot.getChild(3).getChild(0)
        for (i in 1 until moreDetail.childCount step 2) {
            val tag = moreDetail.getChild(i).text.toString()
            if (tag in listOf("当前状态", "商品", "商户全称", "支付时间", "支付方式", "交易单号")) {
                Log.i(tag, moreDetail.getChild(i + 1).getChild(0).text.toString())
            } else if (tag in listOf("商户单号")) {
                Log.i(tag, moreDetail.getChild(i + 2).getChild(0).text.toString())
            }
        }
    }
}