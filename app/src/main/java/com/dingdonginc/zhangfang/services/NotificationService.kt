package com.dingdonginc.zhangfang.services

import android.app.Notification
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.dingdonginc.zhangfang.R

class NotificationService(private val context: Context) {
    fun sendAutoTally(amount: Int) {
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, "自动记账")
        } else {
            Notification.Builder(context)
        }
        builder.setContentTitle("自动记账成功")
        builder.setContentText("成功记录一笔${amount/100}.${amount%100}元的支出")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }
}