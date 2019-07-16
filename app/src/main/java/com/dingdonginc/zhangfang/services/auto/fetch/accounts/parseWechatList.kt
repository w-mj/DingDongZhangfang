package com.dingdonginc.zhangfang.services.auto.fetch.accounts

import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo

/**
 * 处理微信账单列表
 */
object ParseWechatList{
    fun parse(itemList: List<AccessibilityNodeInfo>): Unit{
        if (itemList.isEmpty())
            return
        val billingItemList = itemList[0].parent?.parent ?: return

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
}