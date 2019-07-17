package com.dingdonginc.zhangfang.services.notification

import android.service.notification.StatusBarNotification

/**
 * 监视通知
 */
interface INotificationMonitoringService {
     fun onNotificationPosted(sbn: StatusBarNotification)
}