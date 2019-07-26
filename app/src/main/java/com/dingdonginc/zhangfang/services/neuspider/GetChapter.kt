package com.dingdonginc.zhangfang.services.neuspider

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException
import java.net.*


class GetChapter {
    fun get(): Bitmap? {
        try {
            val url = URL("http://ecard.neu.edu.cn/SelfSearch/validateimage.ashx?0.13349613616620848")
//            CookieHandler.setDefault(CookieManager(null, ACCEPT_ALL))
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.doInput = true
            val inputStream = connection.inputStream
            val shit = CookieHandler.getDefault()
            val fuck = CookieHandler.getDefault().get(URI("http://ecard.neu.edu.cn"), connection.headerFields)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e : IOException) {
            Log.d("GetChapter", "获取验证码失败")
            e.printStackTrace()
        }
        return null
    }
}