package com.dingdonginc.zhangfang.services.notification
import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.TextUtils
import android.util.Log


/**
 * 监视和获取通知不处理
 */
class NotificationMonitoringService : NotificationListenerService(), INotificationMonitoringService {
    //来通知的时候调用
    override fun onNotificationPosted(statusBarNotification: StatusBarNotification) {
        if ("com.eg.android.AlipayGphone" != statusBarNotification.packageName) return

        val notification = statusBarNotification.notification ?: return
        val extras = notification.extras
        if (extras != null) {
            val time = statusBarNotification.postTime
            Log.i("time",time.toString())

            val packageName = statusBarNotification.packageName
            Log.i("packageName", packageName)

            val title = extras.getString(Notification.EXTRA_TITLE, "0")
            Log.i("title", title)

            val content = extras.getString(Notification.EXTRA_TEXT, "123")
            Log.i("content", content)

            if (!TextUtils.isEmpty(content) && "com.eg.android.AlipayGphone" == packageName) {
               // pendingIntent = notification.contentIntent
            }
        }
    }
}
