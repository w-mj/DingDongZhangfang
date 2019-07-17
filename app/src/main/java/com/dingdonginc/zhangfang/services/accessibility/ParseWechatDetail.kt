package com.dingdonginc.zhangfang.services.accessibility
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import java.lang.Exception

object ParseWechatDetail {
    fun parse(item: AccessibilityNodeInfo) {
        if (item.className.toString() != "android.webkit.WebView") {
            Log.e(item.className.toString(), "class is not a webview")
            return
        }
        var detailRoot:AccessibilityNodeInfo? = null
        try{
            detailRoot = item.getChild(0).getChild(0)
            Log.i("商家名", detailRoot.getChild(1).text.toString())
            Log.i("金额", detailRoot.getChild(2).text.toString())
            val moreDetail = detailRoot.getChild(3).getChild(0)
            for (i in 1 until moreDetail.childCount step 2) {
                val child = moreDetail.getChild(i)
                val tag = child.text.toString()
                if (tag in listOf("当前状态", "商品", "商户全称", "支付时间",
                        "转账时间", "支付方式", "交易单号", "转账单号")) {
                    Log.i(tag, moreDetail.getChild(i + 1).getChild(0).text.toString())
                } else if (tag in listOf("商户单号")) {
                    val child1Str = moreDetail.getChild(i + 1).getChild(0).text.toString()
                    var merchant:String?
                    merchant = if (child1Str == "可在支持的商户扫码退款") {
                        moreDetail.getChild(i + 2).getChild(0).text.toString()
                    } else {
                        child1Str
                    }
                    Log.i(tag, merchant)
                }
//                child.recycle()
            }
        } catch (e: Exception) {
            Log.e("Error", e.toString())
        }
    }
}