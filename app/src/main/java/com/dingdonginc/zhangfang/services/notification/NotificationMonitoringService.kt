package com.dingdonginc.zhangfang.services.notification
import java.util.logging.LogRecord
import java.util.logging.Logger
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import java.util.TreeMap

class NotificationMonitoringService : NotificationListenerService() {
    //来通知的时候调用
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notification = sbn.notification ?: return

        var pendingIntent: PendingIntent? = null
        //获取通知
        val extras = notification.extras
        if (extras != null) {
            //包名
            val pkg = sbn.packageName
            Log.i("pkg", pkg)
            //获取通知标题
            val title = extras.getString(Notification.EXTRA_TITLE, "")
            Log.i("title", title)
            //获取通知内容
            val content = extras.getString(Notification.EXTRA_TEXT, "")
            Log.i("content", content)
            if (!TextUtils.isEmpty(content) && "com.eg.android.AlipayGphone" == pkg) {
                pendingIntent = notification.contentIntent
            }
        }
    }
}
