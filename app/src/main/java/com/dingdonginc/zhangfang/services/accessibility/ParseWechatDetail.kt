package com.dingdonginc.zhangfang.services.accessibility

import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.TagFactory
import com.dingdonginc.zhangfang.models.WalletFactory
import com.dingdonginc.zhangfang.services.AccountService
import com.dingdonginc.zhangfang.services.WalletService
import org.kodein.di.generic.instance
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

object ParseWechatDetail {
    fun parse(item: AccessibilityNodeInfo) {
        if (item.className.toString() != "android.webkit.WebView") {
            Log.e(item.className.toString(), "class is not a webview")
            return
        }
        var detailRoot: AccessibilityNodeInfo? = null
        val dataMap: HashMap<String, String> = HashMap()
        try {
            detailRoot = item.getChild(0).getChild(0)
            dataMap["商家名"] = detailRoot.getChild(1).text.toString()
            dataMap["金额"] = detailRoot.getChild(2).text.toString()
            Log.i("商家名", dataMap["商家名"]!!)
            Log.i("金额", dataMap["金额"]!!)
            val moreDetail = detailRoot.getChild(3).getChild(0)
            for (i in 1 until moreDetail.childCount step 2) {
                val child = moreDetail.getChild(i)
                val tag = child.text.toString()
                val childStr = moreDetail.getChild(i + 1).getChild(0).text.toString()
                when (tag) {
                    "当前状态", "商品", "商户全称", "支付方式" -> {
                        dataMap[tag] = childStr
                    }
                    "支付时间", "转账时间" -> {
                        dataMap["时间"] = childStr
                    }
                    "交易单号", "转账单号" -> {
                        dataMap["单号"] = childStr
                    }
                    "商户单号" -> {
                        val merchant = if (childStr == "可在支持的商户扫码退款") {
                            moreDetail.getChild(i + 2).getChild(0).text.toString()
                        } else {
                            childStr
                        }
                        dataMap["商户单号"] = merchant
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.toString())
        }
        Log.i("微信自动记账", "账单详情")
        for (a in dataMap) {
            Log.i(a.key, a.value)
        }
        val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA)
        val walletFactory: WalletFactory by App.getKodein().instance()
        val wallet = walletFactory.wechatBalance()
        val tagFactory: TagFactory by App.getKodein().instance()
        val acc = Account(
            wallet = wallet,
            amount = ((dataMap["金额"]!!).toFloat() * 100).toInt(),
            tag = tagFactory.cloth(),
            time = formatter.parse(dataMap["时间"]!!)!!,
            partner = (dataMap["商户全称"] ?: dataMap["商家名"])!!,
            generatedId = dataMap["单号"]
        )
        val accountService by App.getKodein().instance<AccountService>()
        accountService.addAccount(acc)
    }
}